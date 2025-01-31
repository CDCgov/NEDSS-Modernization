import os
from seleniumpagefactory.Pagefactory import PageFactory
from dotenv import load_dotenv


class addLabReportPage(PageFactory):
    def __init__(self, driver):
        self.driver = driver

    load_dotenv(override=True)

    locators = {
        'patient_tab': ('XPATH', "//td[contains(text(),'Patient')][1]"),
        'lab_report_tab': ('XPATH', "//td[contains(text(),'Lab Report')][1]"),
        'reporting_facility_lookup_txtbox': ('ID', 'NBS_LAB365Text'),
        'program_area_txtbox': ('NAME', 'INV108_textbox'),
        'resulted_test_search_btn': ('ID', 'NBS_LAB220Search')
    }

    def enter_username(self):
        username = os.getenv("USERNAME")
        self.username_txtbox.set_text(username)

    def enter_password(self):
        self.password_txtbox.set_text(os.getenv("PASSWORD"))

    def click_submit(self):
        self.submit_btn.click()
