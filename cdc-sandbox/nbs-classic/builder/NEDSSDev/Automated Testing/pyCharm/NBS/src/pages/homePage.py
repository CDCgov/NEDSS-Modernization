import time
from seleniumpagefactory.Pagefactory import PageFactory


# from src.pages.loginPage import loginPage
# from dotenv import load_dotenv, find_dotenv


class homepage(PageFactory):

    def __init__(self, driver):
        self.driver = driver

    locators = {
        'lastname_txtbox': ('ID', 'DEM102'),
        'firstname_txtbox': ('ID', 'DEM104'),
        'dateofbirth_txtbox': ('ID', 'DEM115'),
        'currentsex_txtbox': ('NAME', 'DEM114_textbox'),
        'currentsex_imgbtn': ('NAME', 'DEM114_button'),
        'search_btn': ('XPATH', "//input[@value='Search']")
    }

    def enter_lastname(self):
        #tm = time.strftime('%a, %d %b %Y %H:%M:%S')
        tm = time.strftime("%Y%m%d%H%M%S")
        self.lastname_txtbox.set_text('QA_' + tm)

    def enter_firstname(self):
        self.firstname_txtbox.set_text('Automation')

    def enter_dateofbirth(self):
        self.dateofbirth_txtbox.set_text('01/01/1960')

    def enter_currentsex(self):
        self.currentsex_txtbox.set_text("Female")

    def click_search(self):
        self.search_btn.click()

