import os
import allure
from seleniumpagefactory.Pagefactory import PageFactory
from dotenv import load_dotenv

@allure.severity(allure.severity_level.NORMAL)
class loginPage(PageFactory):
    def __init__(self, driver):
        self.driver = driver
    load_dotenv(override=True)

    locators = {
        'username_txtbox': ('ID', "id_UserName"),
        'password_txtbox': ('ID', 'id_Password'),
        'submit_btn': ('NAME', 'id_Submit_bottom_ToolbarButtonGraphic')
        }

    def enter_username(self):
        username = os.getenv("USERNAME")
        self.username_txtbox.set_text(username)

    def enter_password(self):
        self.password_txtbox.set_text(os.getenv("PASSWORD"))

    def click_submit(self):
        self.submit_btn.click()
