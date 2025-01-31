from seleniumpagefactory.Pagefactory import PageFactory


class patientFilePage(PageFactory):

    def __init__(self, driver):
        self.driver = driver

    locators = {
                'events_tab': ('XPATH', "(//td[contains(text(),'Events')])[1]")
    }

    def click_eventstab(self):
        self.events_tab.click()
