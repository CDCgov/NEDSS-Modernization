
from selenium import webdriver
import time
from selenium.common.exceptions import NoSuchElementException



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

sysManagment = driver.find_element_by_link_text('System Management')
sysManagment.click()
time.sleep(.1)

pageManagment = driver.find_element_by_xpath(".//*[@id='systemAdmin5']/thead/tr/th/a/img")
pageManagment.click()
time.sleep(.1)

manageQuestions = driver.find_element_by_link_text('Manage Value Sets')
manageQuestions.click()
time.sleep(.1)

next_p = driver.find_element_by_link_text('Next')
if(next_p.is_enabled()):
    print("next_p.is_enabled()",next_p.is_enabled())

# q_id = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[" + str(i) + "]/td[4]")
q_id = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[1]/td[4]")
print("q_id.text: ", q_id.text)

q_exclude = ['PHVS_CLINICALSYNDROMESECONDARY_ARBOVIRUS','PHVS_DETECTIONMETHOD_STD','PHVS_NONTREPONEMALTESTRESULT_STD',
             'PHVS_LABTESTRESULTQUALITATIVE_NND','PHVS_NEWBORNCOMPLICATIONS_ARBOVIRUS','PHVS_NONTREPONEMALSEROLOGICTEST_STD',
             'PHVS_PERFORMINGLABORATORYTYPE_VPD','PHVS_POSNEG4FOLDRISENOTDONE_CDC','PHVS_PREGNANCYCOMPLICATIONS_ARBOVIRUS',
             'PHVS_PREGNANCYOUTCOME_CDC','PHVS_NONTREPONEMALTESTRESULT_STD','PHVS_TRANSMISSIONMODE_ARBOVIRUS','PHVS_TRAVELREASON_HEPATITISA',
             'PHVS_TREPONEMALSEROLOGICTEST_STD','PHVS_SEXUALPREFERENCE_NETSS','VAC_MFGR_LOCAL']

for i in range(1, 34):
    next_p.click()
    next_p = driver.find_element_by_link_text('Next')
    time.sleep(2)

while_stopper = True
while while_stopper:
    try:
        next_p = driver.find_element_by_link_text('Next')
    except NoSuchElementException:
        while_stopper = False

    for i in range(1, 21):

        q_id = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[" + str(i) + "]/td[4]")
        print("q_id.text: ", q_id.text)
        q_id_text = q_id.text
        if q_id_text in q_exclude:
            continue
        else:

            # if q_id_text in q_exclude:
            #     continue
            # else:
            q_edit = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[" + str(i) + "]/td[2]/a/img")
            q_edit.click()

            q_submit = driver.find_element_by_name('submitA')
            q_submit.click()

            back_to_library = driver.find_element_by_id('manageLink')
            back_to_library.click()

    time.sleep(1)
    print("next_p.click()")
    next_p = driver.find_element_by_link_text('Next')
    next_p.click()
    time.sleep(2)