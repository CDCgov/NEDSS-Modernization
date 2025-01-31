import time
from selenium import webdriver
from selenium.common.exceptions import ElementNotVisibleException



# driver = webdriver.Chrome('C:\\Py\\chromedriver_win32_new\\chromedriver.exe',
#                           service_args=['--ignore-ssl-errors=true', '--ssl-protocol=TLSv1'])

class nbs:


    def login_w_pks(driver):
        print("login_w_pks Starting")
        # driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

        driver.get('http://www.google.com/xhtml')
        time.sleep(1)
        # driver.get('http://35.175.68.52:7001/nbs/login')
        # driver.get('http://35.172.177.22:7001/nbs/login')
        # driver.get('http://10.62.0.106:7001/nbs/login')
        driver.get('http://35.170.137.196:7001/nbs/login')

        time.sleep(1)

        login_box = driver.find_element_by_id('id_UserName')
        login_box.send_keys('pks')

        submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
        submit_login.click()
        print("login_w_pks ending")

    def testpatientA(driver):
        Last_Name = driver.find_element_by_id('DEM102')
        checkelement = 0
        while checkelement != 1:
            if Last_Name.is_displayed():
                checkelement = 1
            else:
                time.sleep(1)
                print('Firefox: Wait for home page to load')
        Last_Name.send_keys('TestPatientA')
        First_Name = driver.find_element_by_id('DEM104')
        First_Name.send_keys('TestPatientA')
        Click_Search = driver.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
        Click_Search.click()

        time.sleep(2)

        Click_ID_Link = driver.find_element_by_xpath('.//*[@id=\'searchResultsTable\']/tbody/tr[1]/td[1]/a')
        Click_ID_Link.click()

        time.sleep(3)

        Events_Tab = driver.find_element_by_id('tabs0head1')
        Events_Tab.click()

        Add_New_Lab = driver.find_element_by_id('subsect_Lab')
        Add_New_Lab.click()

        click_lab_link = driver.find_element_by_xpath(".//*[@id='eventLabReport']/tbody/tr/td[1]/a")
        click_lab_link.click()
    def select_existing_pat(driver):

        Last_Name = driver.find_element_by_id('DEM102')
        checkelement = 0
        while checkelement != 1:
            if Last_Name.is_displayed():
                checkelement = 1
            else:
                time.sleep(1)
                print('Firefox: Wait for home page to load')
        Last_Name.send_keys('Pertussis')
        First_Name = driver.find_element_by_id('DEM104')
        First_Name.send_keys('Pertussis')

        DOB = driver.find_element_by_id('DEM115')
        DOB.send_keys('05/09/1988')

        CurrentSex = driver.find_element_by_name('DEM114_textbox')
        CurrentSex.send_keys('Male')

        Click_Search = driver.find_element_by_xpath(
            './/*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
        Click_Search.click()

        select_patient = driver.find_element_by_xpath('//*[@id="searchResultsTable"]/tbody/tr[1]/td[1]/a')
        select_patient.click()



    def add_pertussis_patient(driver,x):
        Last_Name = driver.find_element_by_id('DEM102')
        checkelement = 0
        while checkelement != 1:
            if Last_Name.is_displayed():
                checkelement = 1
            else:
                time.sleep(1)
                print('Firefox: Wait for home page to load')
        Last_Name.send_keys('Pertussis'+str(x))
        First_Name = driver.find_element_by_id('DEM104')
        First_Name.send_keys('Pertussis'+str(x))

        DOB = driver.find_element_by_id('DEM115')
        DOB.send_keys('05/09/1988')

        CurrentSex = driver.find_element_by_name('DEM114_textbox')
        CurrentSex.send_keys('Male')

        Click_Search = driver.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
        Click_Search.click()

        Add_New_Patient = driver.find_element_by_name('Submit')
        Add_New_Patient.click()

        add_zip = driver.find_element_by_id('DEM163')
        add_zip.send_keys('30342')

        Add_New_Patient_submit = driver.find_element_by_id('Submit')
        Add_New_Patient_submit.click()

    def add_inv_from_patient_file(driver):
        events_tab = driver.find_element_by_id('tabs0head1')
        events_tab.click()
        time.sleep(2)
        # try:
        #     add_inv = driver.find_element_by_xpath('//*[@id="subsect_Inv"]/table/tbody/tr/td[3]/input')
        #     add_inv.click()
        # except ElementNotVisibleException:
        #     add_inv = driver.find_element_by_xpath(
        #         '//input[@onclick="getWorkUpPage(\'/nbs/ViewFile1.do?ContextAction=AddInvestigation\');"]')
        #     add_inv.click()
        add_inv = driver.find_element_by_xpath(
                '//input[@onclick="getWorkUpPage(\'/nbs/ViewFile1.do?ContextAction=AddInvestigation\');"]')
        add_inv.click()

        time.sleep(1)



        select_condition = driver.find_element_by_name('ccd_textbox')
        select_condition.send_keys('Pertussis')

        submit_condition_selection = driver.find_element_by_id('Submit')
        submit_condition_selection.click()

    def test_patient_A_file(driver):
        Last_Name = driver.find_element_by_id('DEM102')
        checkelement = 0
        while checkelement != 1:
            if Last_Name.is_displayed():
                checkelement = 1
            else:
                time.sleep(1)
                print('Firefox: Wait for home page to load')
        Last_Name.send_keys('TestPatientA')
        First_Name = driver.find_element_by_id('DEM104')
        First_Name.send_keys('TestPatientA')
        Click_Search = driver.find_element_by_xpath(
            './/*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
        Click_Search.click()

        time.sleep(2)

        Click_ID_Link = driver.find_element_by_xpath('.//*[@id=\'searchResultsTable\']/tbody/tr[1]/td[1]/a')
        Click_ID_Link.click()

        time.sleep(3)

        Events_Tab = driver.find_element_by_id('tabs0head1')
        Events_Tab.click()





