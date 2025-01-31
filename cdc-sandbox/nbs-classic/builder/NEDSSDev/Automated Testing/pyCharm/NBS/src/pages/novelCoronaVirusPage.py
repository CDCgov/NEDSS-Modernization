from seleniumpagefactory.Pagefactory import PageFactory


class novelCoronaVirusPage(PageFactory):

    def __init__(self, driver):
        self.driver = driver

    locators = {
                'invest_addnew_btn': ('XPATH', "(//input[@name='Add'])[1]"),
                'condition_txtbox': ('NAME', "ccd_textbox"),
                'submit_condition_btn': ('XPATH', "(//input[@name='Submit'])[2]"),
                'caseinfo_tab': ('XPATH', "(//td[contains(text(),'Case Info')])[1]"),
                'jurisdiction_txtbox': ('NAME', "INV107_textbox"),
                'submitTop_btn': ('ID', "SubmitTop"),
                'returntofile_link': ('XPATH', "//a[normalize-space()='Return To File: Events']")
    }

    def click_addNew_investigation(self):
        self.invest_addnew_btn.click()

    def enter_condition(self):
        self.condition_txtbox.set_text("2019 Novel Coronavirus")

    def click_submitcondition(self):
        self.submit_condition_btn.click()

    def click_caseinfo(self):
        self.caseinfo_tab.click()

    def enter_jurisdiction(self):
        self.jurisdiction_txtbox.set_text("Clayton County")

    def click_submitTop(self):
        self.submitTop_btn.click()

    def click_returntofile(self):
        self.returntofile_link.click()
