import time
import os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from src.pages.loginPage import loginPage
from src.pages.homePage import homepage
from src.pages.searchResultsPage import searchResultsPage
from src.pages.addNewPatientPage import addNewPatientPage
from src.pages.patientFilePage import patientFilePage
from src.pages.novelCoronaVirusPage import novelCoronaVirusPage
from dotenv import load_dotenv


def test_demo():

    load_dotenv(override=True)
    # service = Service(executable_path='C:\\GitHub\\NEDSSDev\\Automated Testing\\pyCharm\\NBS\\chromeDriver\\chromedriver.exe')
    driver = webdriver.Chrome()
    driver.get(os.getenv("NBS_URL"))

    login = loginPage(driver)
    login.enter_username()
    login.click_submit()
    time.sleep(5)
    nbs_title = driver.title
    print(nbs_title)
    if nbs_title == "NBS Dashboard":
        assert True
    else:
        assert False

    homePage = homepage(driver)
    homePage.enter_lastname()
    homePage.enter_firstname()
    homePage.enter_dateofbirth()
    homePage.enter_currentsex()
    homePage.click_search()
    time.sleep(5)

    searchresults = searchResultsPage(driver)
    searchresults.click_addnew()
    time.sleep(5)

    addPatient = addNewPatientPage(driver)
    addPatient.click_submitAddNew()
    time.sleep(5)

    eventstab = patientFilePage(driver)
    eventstab.click_eventstab()
    time.sleep(5)

    covidcondition = novelCoronaVirusPage(driver)
    covidcondition.click_addNew_investigation()
    covidcondition.enter_condition()
    covidcondition.click_submitcondition()
    covidcondition.click_caseinfo()
    covidcondition.enter_jurisdiction()
    covidcondition.click_submitTop()
    covidcondition.click_returntofile()
