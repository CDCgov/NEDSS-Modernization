from seleniumpagefactory.Pagefactory import PageFactory


class searchResultsPage(PageFactory):

    def __init__(self, driver):
        self.driver = driver

    locators = {
                'addnew_btn': ('XPATH', "(//input[@name='Submit'])[1]")
    }

    def click_addnew(self):
        self.addnew_btn.click()