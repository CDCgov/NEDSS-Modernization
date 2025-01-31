import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By


class GoogleOrgSearch(unittest.TestCase):

    def setUp(self):
       # self.driver = webdriver.Firefox()
       self.driver = webdriver.Chrome("C:\\Py\\chromedriver_win32\\chromedriver.exe")

    def test_google_search_page(self):
        driver = self.driver
        driver.get("http://www.quackit.com/html/codes/html_popup_window_code.cfm")
        str1 = driver.title
        print(str1)

        # get the window handles using window_handles( ) method
        window_before = driver.window_handles[0]
        print(window_before)


# since the pop-up is now in an iframe
driver.switch_to.frame(driver.find_element_by_name('result1'))
driver.find_element_by_link_text('Open a popup window').click()

# get the window handle after a new window has opened
window_after = driver.window_handles[1]

# switch on to new child window
driver.switch_to.window(window_after)
str2 = driver.title
print(str2)
print(window_after)

# assert that main window and child window title don't match
self.assertNotEqual(str1, str2)
print('This window has a different title')

# switch back to original window
driver.switch_to.window(window_before)

# assert that the title now match
self.assertEqual(str1, driver.title)
print('Returned to parent window. Title now match')


def tearDown(self):
    self.driver.close()


if __name__ == "__main__":
    unittest.main()