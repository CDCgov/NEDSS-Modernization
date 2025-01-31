from selenium.webdriver.common.by import By


class LoginPage:
    # locators for NBS Landing Page
    landing_Page_Title = '//*[@id="frm"]/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[1]'
    # locators for login
    login_page_username = "id_UserName"
    login_page_password = "id_Password"
    submit_button_bottom_login_xpath = "//*[@id='id_Submit_bottom_ToolbarButtonGraphic']"
    submit_button_top_login_xpath = "//*[@id='id_Submit_top_ToolbarButtonGraphic']"
    label_login_page_username_xpath = "//*[@id='id_Login_Section']/tbody/tr[4]/td[1]/label"
    label_login_page_password_xpath = "//*[@id='id_Login_Section']/tbody/tr[5]/td[1]/label"
    label_submit_button_bottom_xpath = "//*[@id='id_Submit_bottom_ToolbarButtonLabel']"
    label_submit_button_top_xpath = "//*[@id='id_Submit_top_ToolbarButtonLabel']"
    logo_nbs_login_page = "//*[@id='frm']/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[2]/img"

    # locator for NBS logout
    logout_from_nbs_app = "Logout"

    # init method
    def __init__(self, driver):
        self.driver = driver

    # Actions
    def logoutFromNBSApplication(self):
        self.driver.find_element(By.LINK_TEXT, self.logout_from_nbs_app).click()

    def getLandingPageTitle(self):
        elem = self.driver.find_element(By.XPATH, self.landing_Page_Title)
        return elem.text

    def getLabelLoginPageUserName(self):
        elem = self.driver.find_element(By.XPATH, self.label_login_page_username_xpath)
        return elem.text

    def getLabelLoginPagePassword(self):
        elem = self.driver.find_element(By.XPATH, self.label_login_page_password_xpath)
        return elem.text

    def getLabelBottomSubmitButton(self):
        return self.driver.find_element(By.XPATH, self.label_submit_button_bottom_xpath).text

    def getLabelTopSubmitButton(self):
        return self.driver.find_element(By.XPATH, self.label_submit_button_top_xpath).text

    def getLogoDisplayedOnLoginPage(self):
        return self.driver.find_element(By.XPATH, self.logo_nbs_login_page).is_displayed()

    def setUserName(self, username):
        self.driver.find_element(By.ID, self.login_page_username).clear()
        self.driver.find_element(By.ID, self.login_page_username).send_keys(username)

    def setUserPassword(self, password):
        self.driver.find_element(By.ID, self.login_page_password).clear()
        self.driver.find_element(By.ID, self.login_page_password).send_keys(password)

    def clickBottomSubmitButton(self):
        self.driver.find_element(By.XPATH, self.submit_button_bottom_login_xpath).click()

    def clickTopSubmitButton(self):
        self.driver.find_element(By.XPATH, self.submit_button_top_login_xpath).click()
