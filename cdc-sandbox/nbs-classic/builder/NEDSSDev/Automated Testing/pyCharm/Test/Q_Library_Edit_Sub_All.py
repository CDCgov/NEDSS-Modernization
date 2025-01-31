
from selenium import webdriver
import time
from selenium.common.exceptions import NoSuchElementException

driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
# driver.get('http://nedss-perform1:7001/nbs/login')
# driver.get('http://54.208.14.194:7001/nbs/login')
# driver.get('http://35.172.177.22:7001/nbs/login')
driver.get('http://10.62.0.184:7001/nbs/login')
# driver.get('http://35.175.68.52:7001/nbs/login')



login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()


checkelement = 0
while checkelement != 1:
    try:
        Last_Name = driver.find_element_by_id('DEM102')
    except:
        continue

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

manageQuestions = driver.find_element_by_link_text('Manage Questions')
manageQuestions.click()
time.sleep(.1)

q_count = driver.find_element_by_xpath(".//*[@id='result']/table/tbody/tr/td/span[1]/b")
q_count_txt = q_count.text

print(q_count_txt)

next_p = driver.find_element_by_link_text('Next')
if(next_p.is_enabled()):
    print("next_p.is_enabled()",next_p.is_enabled())

for i in range(1, 105):
    next_p = driver.find_element_by_link_text('Next')
    next_p.click()
    time.sleep(2)

# demo data on dbsql/test1
# q_exclude = ['91454002','ARB050','DEM126_I','DEM127','DEM139','DEM142','DEM159','DEM159_W','DEM160','DEM160_W','DEM168_W',
#              'DEM175_W','DEM225','DEM250','INV143','INV144','INV159','INV165','INV166','INV2001','INV2002','INV202','INV303',
#              'INV504','INV886','INV908_1','INV908_2','INV908_3','ME135000','NBS003','NBS056','NBS057','NBS095','NBS096','NBS097',
#              'NBS098','NBS100','NBS101','NBS102','NBS102_W','NBS103','NBS104','NBS112','NBS113','NBS114','NBS140','NBS142','NBS146',
#              'NBS149','NBS159','NBS162','NBS167','NBS268','NBS338_OTH','NBS367','NBS369','NBS451','NBS460','VAC101','VAR151']



q_exclude = ['91454002','ARB050','DEM126_I','DEM127','DEM139','DEM142','DEM159','DEM159_W','DEM160','DEM160_W','DEM168_W',
             'DEM175_W','DEM225','DEM250','INV143','INV144','INV159','INV165','INV166','INV2001','INV2002','INV202','INV303',
             'INV504','INV886','INV908_1','INV908_2','INV908_3','ME135000','NBS003','NBS056','NBS057','NBS095','NBS096','NBS097',
             'NBS098','NBS100','NBS101','NBS102','NBS102_W','NBS103','NBS104','NBS112','NBS113','NBS114','NBS140','NBS142','NBS146',
             'NBS149','NBS159','NBS162','NBS167','NBS268','NBS338_OTH','NBS367','NBS369','NBS451','NBS460','VAC101','VAR151',
             ### AL specific
             '233604007','25064002','271807003','338822','386661006','62315008','667469','68962001','AL10173','AL10176','AL10177',
             'AL27128','AL27146','AL27183','AL27186','AL27187','AL28125','AL28127','AL28134','AL28135','AL29142','AL43105','AL61103',
             'AL61104','AL62103','AL62104','AL62105','AL62106','AL62107','AL64128','AL64135','AL65110','AL67148','AL70103','AL70104',
             'AL73100','AL79103','AL90125','AL90133','AL90136','AL90148','AL91103','AL91104','AL91105','AL91106','AL91107','AL91109',
             'AL91110','AL93102','AL97101','AL97102','ARB031','FDD_Q_1049','FDD_Q_225','FDD_Q_28','FDD_Q_4','FDD_Q_947','FDD_Q_960',
             'IN11007','INV936','NBS248','NBS_LAB201','NBS_LAB279','NBS_LAB313','TB208']


while_stopper = True
while while_stopper:
    try:
        next_p = driver.find_element_by_link_text('Next')
    except NoSuchElementException:
        while_stopper = False

    for i in range(1,21):



        q_id = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr["+str(i)+"]/td[4]")
        is_active = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[" + str(i) + "]/td[8]")
        # print (is_active.text)
        print("q_id.text: ",q_id.text)
        q_id_text = q_id.text

        if q_id_text in q_exclude:
            continue
        elif is_active.text == 'Inactive':
            pass
        else:
            q_edit = driver.find_element_by_xpath(".//*[@id='parent']/tbody/tr[" + str(i) + "]/td[2]/a/img")
            q_edit.click()

            q_submit = driver.find_element_by_name('Submit')
            q_submit.click()

            try:
                back_to_library = driver.find_element_by_id('manageLink')
                back_to_library.click()
            except NoSuchElementException:
                error_message = driver.find_element_by_xpath(".//*[@id='errorMessages']/ul/li")
                print(q_id.text + " " + error_message.text)


    time.sleep(1)
    print("next_p.click()")
    next_p = driver.find_element_by_link_text('Next')
    next_p.click()
    time.sleep(2)

    # if(next_p.is_enabled()):
    #     continue
    # else:
    #     while_stopper = False




