from src.page_objects.login.login import LoginPage
from src.utilities.log_config import LogUtils
from src.utilities.properties import Properties
import time
import os


class TestLogout:
    baseURL = Properties.getApplicationURL()
    username = Properties.getUserName()
    password = Properties.getUserPassword()
    logger = LogUtils.loggen(__name__)

    def test_logout_from_nbs(self, setup):
        self.logger.info("****started test_logout_from_nbs ****")
        self.driver = setup
        self.driver.get(self.baseURL)
        self.lp = LoginPage(self.driver)
        self.lp.setUserName(self.username)
        self.lp.setUserPassword(self.password)
        self.lp.clickBottomSubmitButton()
        act_title = self.driver.title
        self.logger.info("****test_logout_from_nbs title ****" + act_title)
        if act_title == "NBS Dashboard":
            self.logger.info("****test_logout_from_nbs test passed ****")
            time.sleep(3)
            self.lp.logoutFromNBSApplication()
            logout_title = self.driver.title
            if logout_title == "NBS":
                self.logger.info("****test_nbs_login_by_top_submit_button:NBS logout test PASSED ****")
                assert True
            else:
                self.logger.info("****test_nbs_login_by_top_submit_button:NBS logout test FAILED ****")
                pytest.fail("test_nbs_login_by_top_submit_button:logout test failed.")
            time.sleep(3)
            self.driver.close()
            assert True
        else:
            self.logger.error("****test_logout_from_nbs test failed ****")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_login.png")
            self.driver.close()
            assert False
