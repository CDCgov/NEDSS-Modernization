from seleniumpagefactory.Pagefactory import PageFactory


class addNewPatientPage(PageFactory):

    def __init__(self, driver):
        self.driver = driver

    locators = {
                'submit_btn': ('XPATH', "(//input[@id='Submit'])[1]")
    }

    def click_submitAddNew(self):
        self.submit_btn.click()
