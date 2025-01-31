import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from src.page_objects.login.login import LoginPage
from src.utilities.log_config import LogUtils
from src.utilities.properties import Properties
import time
import os


class TestLogin:
    baseURL = Properties.getApplicationURL()
    username = Properties.getUserName()
    password = Properties.getUserPassword()
    logger = LogUtils.loggen(__name__)

    # Testcase for NBS Landing Page.
    """def test_landing_page(self, setup):
        self.logger.info("****Started test_landing_page****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.driver.maximize_window()
        act_title = self.driver.title
        page_header = self.lp.getLandingPageTitle()
        self.logger.info("****test_landing_page title ****" + act_title)
        self.logger.info("****test_landing_page Header****" + self.lp.getLandingPageTitle())
        if (act_title == "NBS") and (page_header == "NBS Login"):
            self.logger.info("****test_landing_page passed ****")
            time.sleep(5)
            self.driver.close()
            assert True
        else:
            self.logger.error("****test_landing_page failed ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_landing_page.png")
            self.driver.close()
            assert False

    # Testcase for NBS Login Labels Validation.
    def test_Login_page_labels_validation(self, setup):
        self.logger.info("****Started test_Login_page_labels_validation****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.driver.maximize_window()
        page_header = self.lp.getLandingPageTitle()
        if page_header == "NBS Login":
            self.logger.info("****test_Login_page_labels_validation:logged-in****")
            time.sleep(5)
            login_username_label = self.lp.getLabelLoginPageUserName()
            login_password_label = self.lp.getLabelLoginPagePassword()
            login_bottom_submit_button_label = self.lp.getLabelBottomSubmitButton()
            login_top_submit_button_label = self.lp.getLabelTopSubmitButton()
            if ((login_username_label == "User Name:") and (login_password_label == "Password:")
                    and (login_bottom_submit_button_label == "Submit") and (login_top_submit_button_label == "Submit")):
                self.logger.info("username,password and submit button labels test passed")
                self.driver.close()
                assert True
            else:
                self.logger.error("****test_Login_page_labels_validation failed ****")
                self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_labels.png")
                self.driver.close()
                assert False
        else:
            self.logger.error("****test_Login_page_labels_validation failed while landing NBS ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_landing.png")
            self.driver.close()
            assert False

    # Testcase for NBS Login Page for Bottom Submit button
    def test_login_by_bottom_submit_button(self, setup):
        self.logger.info("****started test_login_by_bottom_submit_button ****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.lp.setUserName(self.username)
        self.lp.setUserPassword(self.password)
        self.lp.clickBottomSubmitButton()
        act_title = self.driver.title
        self.logger.info("****test_login_by_bottom_submit_button title ****" + act_title)
        if act_title == "NBS Dashboard":
            self.logger.info("****test_login_by_bottom_submit_button test passed ****")
            time.sleep(5)
            self.driver.close()
            assert True
        else:
            self.logger.error("****test_login_by_bottom_submit_button test failed ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_bottom.png")
            self.driver.close()
            assert False

    # Testcase for NBS Login Page for top Submit button
    def test_login_by_top_submit_button(self, setup):
        self.logger.info("****started test_login_by_top_submit_button****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.driver.maximize_window()
        self.lp = LoginPage(self.driver)
        self.lp.setUserName(self.username)
        self.lp.setUserPassword(self.password)
        self.lp.clickBottomSubmitButton()
        act_title = self.driver.title
        self.logger.info("****test_login_by_top_submit_button title ****" + act_title)
        if act_title == "NBS Dashboard":
            self.logger.info("****test_login_by_top_submit_button test passed ****")
            time.sleep(5)
            self.driver.close()
            assert True
        else:
            self.logger.error("****test_login_by_top_submit_button test failed ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_top.png")
            self.driver.close()
            assert False"""

    # Testcase for NBS Login Page for Bottom Submit button
    def test_nbs_login_by_bottom_submit_button(self, setup):
        self.logger.info("****started test_nbs_login_by_bottom_submit_button ****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.driver.maximize_window()
        act_title = self.driver.title
        page_header = self.lp.getLandingPageTitle()
        if (act_title == "NBS") and (page_header == "NBS Login"):
            self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS Landing Page PASSED ****")
            assert True
            time.sleep(3)
            login_username_label = self.lp.getLabelLoginPageUserName()
            login_password_label = self.lp.getLabelLoginPagePassword()
            login_bottom_submit_button_label = self.lp.getLabelBottomSubmitButton()
            login_top_submit_button_label = self.lp.getLabelTopSubmitButton()
            is_logo_on_login_page = self.lp.getLogoDisplayedOnLoginPage()
            if ((login_username_label == "User Name:") and (login_password_label == "Password:")
                    and (login_bottom_submit_button_label == "Submit") and (
                            login_top_submit_button_label == "Submit") and is_logo_on_login_page):
                self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS login labels test PASSED ****")
                assert True
            else:
                self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS login labels test FAILED ****")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_labels.png")
                pytest.fail("test_nbs_login_by_bottom_submit_button:label validations are failed.")
            self.lp.setUserName(self.username)
            self.lp.setUserPassword(self.password)
            self.lp.clickBottomSubmitButton()
            act_title = self.driver.title
            if act_title == "NBS Dashboard":
                self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS login test PASSED ****")
                assert True
                time.sleep(4)
                self.lp.logoutFromNBSApplication()
                time.sleep(2)
                logout_title = self.driver.title
                if logout_title == "NBS":
                    self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS logout test PASSED ****")
                    assert True
                else:
                    self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS logout test FAILED ****")
                    pytest.fail("test_nbs_login_by_bottom_submit_button:logout test failed.")
                self.driver.close()
            else:
                self.logger.info(
                    "****test_nbs_login_by_bottom_submit_button:NBS login test FAILED ****" + self.username)
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_bottom.png")
                self.driver.close()
                pytest.fail(f"invalid login username:{self.username}")
        else:
            time.sleep(3)
            self.logger.info("****test_nbs_login_by_bottom_submit_button:NBS Landing Page FAILED ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_nbs_landing.png")
            self.driver.close()
            assert False

    # Testcase for NBS Login Page for top Submit button
    def test_nbs_login_by_top_submit_button(self, setup):
        self.logger.info("****started test_nbs_login_by_top_submit_button ****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.driver.maximize_window()
        act_title = self.driver.title
        page_header = self.lp.getLandingPageTitle()
        if (act_title == "NBS") and (page_header == "NBS Login"):
            self.logger.info("****test_nbs_login_by_top_submit_button:NBS Landing Page PASSED ****")
            assert True
            time.sleep(3)
            login_username_label = self.lp.getLabelLoginPageUserName()
            login_password_label = self.lp.getLabelLoginPagePassword()
            login_bottom_submit_button_label = self.lp.getLabelBottomSubmitButton()
            login_top_submit_button_label = self.lp.getLabelTopSubmitButton()
            is_logo_on_login_page = self.lp.getLogoDisplayedOnLoginPage()
            if ((login_username_label == "User Name:") and (login_password_label == "Password:")
                    and (login_bottom_submit_button_label == "Submit") and (
                            login_top_submit_button_label == "Submit") and is_logo_on_login_page):
                self.logger.info("****test_nbs_login_by_top_submit_button:NBS login labels test PASSED ****")
                assert True
            else:
                self.logger.info("****test_nbs_login_by_top_submit_button:NBS login labels test FAILED ****")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_labels.png")
                pytest.fail("test_nbs_login_by_top_submit_button:label validations are failed.")
            self.lp.setUserName(self.username)
            self.lp.setUserPassword(self.password)
            self.lp.clickTopSubmitButton()
            act_title = self.driver.title
            if act_title == "NBS Dashboard":
                self.logger.info("****test_nbs_login_by_top_submit_button:NBS login test PASSED ****")
                assert True
                time.sleep(4)
                self.lp.logoutFromNBSApplication()
                time.sleep(2)
                logout_title = self.driver.title
                if logout_title == "NBS":
                    self.logger.info("****test_nbs_login_by_top_submit_button:NBS logout test PASSED ****")
                    assert True
                else:
                    self.logger.info("****test_nbs_login_by_top_submit_button:NBS logout test FAILED ****")
                    pytest.fail("test_nbs_login_by_top_submit_button:logout test failed.")
                self.driver.close()
            else:
                self.logger.info(
                    "****test_nbs_login_by_top_submit_button:NBS login test FAILED ****" + self.username)
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login_bottom.png")
                self.driver.close()
                pytest.fail(f"invalid login username:{self.username}")
        else:
            time.sleep(3)
            self.logger.info("****test_nbs_login_by_top_submit_button:NBS Landing Page FAILED ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_nbs_landing.png")
            self.driver.close()
            assert False
