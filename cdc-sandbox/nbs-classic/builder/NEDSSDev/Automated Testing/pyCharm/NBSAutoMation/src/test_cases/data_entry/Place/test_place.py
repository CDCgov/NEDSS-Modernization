from datetime import datetime
from selenium.webdriver.common.by import By
from src.page_objects.data_entry.place import Place
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

import time
import pytest

import os
import random


class TestPlace:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    logger.info(random_number)
    db_utilities = DBUtilities()

    def setup_and_place_handle(self, setup):
        self.driver = setup
        TestPlace.driver_globe = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.place = Place(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Place").click()

    def test_dataEntry_Place_New(self, setup):
        self.logger.info("****CreateNew_Place****")
        self.setup_and_place_handle(setup)
        place = self.driver.title

        if place == NBSConstants.PLACE_SEARCH_SECTION_HEADER_LABEL:
            self.logger.info("**test_add_place_to_nbs_system:NBS org Search Landing Page PASSED**")
            assert True
            self.place.setPlaceLastName(self.random_number)
            self.place.setPlaceSubmit()
            self.place.setPlaceAdd()
            add_place_header_title = self.place.getAddPlaceHeader()

            if add_place_header_title.strip() == NBSConstants.ADD_PLACE_HEADER_TITLE:
                self.logger.info("**test_add_place_to_nbs_system:NBS Add org landing Page PASSED**")
                assert True
                time.sleep(1)
                try:
                    self.place.setPlaceAdminSection(self.random_number)
                    self.place.setPlaceAddressSection(NBSConstants.ADD_PROVIDER_IDENTIFICATION_REPEAT,
                                                      str(self.random_number))
                    self.place.setPlaceTelephoneSection(NBSConstants.ADD_PROVIDER_IDENTIFICATION_REPEAT)
                    time.sleep(2)
                    self.place.addPlaceToSystem()
                except:
                    self.logger.info("Place details already exist " + "PlaceName" +
                                     " New Place is not created")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "test_CreatePLace.png")
                    # self.driver.close()
                    pytest.fail(f"Add Place Test FAILED")

            time.sleep(5)
        else:
            self.logger.info("*NBS Add Place Landing Page FAILED**")
            pytest.fail("*NBS Add Place Landing Page test failed.")

    def test_edit_place_with_typographical_reason(self, setup):
        self.logger.info("****Edit_Place****")
        self.setup_and_place_handle(setup)
        place = self.driver.title
        uid = self.db_utilities.getPlaceUniqueId(str(self.random_number))

        if self.place.performSearchByElement(NBSConstants.ADD_PLACE_LAST_NAME, self.random_number, uid):
            self.logger.info("*Place Search By Last Name PASSED*")
            # click on edit and update
            self.driver.find_element(By.ID, "btnEditT").click()
            time.sleep(2)
            radio_buttons = self.driver.find_elements(By.CSS_SELECTOR, "input[name='reasonForEdit']")
            radio_buttons[NBSConstants.EDIT_PLACE_RADIO_FIRST_OPTION].click()
            time.sleep(2)
            self.place.editPlaceAdminSection(self.random_number, NBSConstants.EDIT_PLACE_RADIO_FIRST_OPTION)
            self.place.editPlaceAddressActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            self.place.editPlaceTelephoneActions(NBSConstants.EDIT_OPTION)
            time.sleep(2)
            self.driver.find_element(By.ID, "btnSubmitB").click()
            assert True
        else:
            # self.driver.close()
            self.logger.info("*Place Search By Last Name FAILED**")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "Place_search_by_ln.png")
            pytest.fail(f"Search By Last Name FAILED")

    def test_edit_place_with_non_typographical_reason(self, setup):
        self.logger.info("****test_edit_place_with_non_typographical_reason****")
        self.setup_and_place_handle(setup)
        place = self.driver.title
        uid = self.db_utilities.getPlaceUniqueId(str(self.random_number))

        if self.place.performSearchByElement(NBSConstants.ADD_PLACE_LAST_NAME, self.random_number, uid):
            self.logger.info("*Place Search By Last Name PASSED*")
            # click on edit and update
            self.driver.find_element(By.ID, "btnEditT").click()
            time.sleep(2)
            radio_buttons = self.driver.find_elements(By.CSS_SELECTOR, "input[name='reasonForEdit']")
            radio_buttons[NBSConstants.EDIT_PLACE_RADIO_SECOND_OPTION].click()
            time.sleep(2)
            place_name = 'lastname_' + str(self.random_number)
            self.place.editPlaceLastName(place_name)
            self.place.editPlaceAddressActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            self.place.editPlaceTelephoneActions(NBSConstants.EDIT_OPTION)
            time.sleep(2)
            self.driver.find_element(By.ID, "btnSubmitB").click()
            self.driver.switch_to.alert.accept()
            time.sleep(2)
            random_quick_code = str(datetime.now().strftime("%d%m%y%H%M%S"))
            self.place.editPlaceAdminSection(random_quick_code, NBSConstants.EDIT_PLACE_RADIO_FIRST_OPTION)
            time.sleep(2)
            self.driver.find_element(By.ID, "btnSubmitB").click()
            assert True
        else:
            # self.driver.close()
            self.logger.info("*Place Search By Last Name FAILED**")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "Place_search_by_ln.png")
            pytest.fail(f"Search By Last Name FAILED")

    def test_place_inactive(self, setup):
        self.logger.info("****test_place_inactive****")
        self.setup_and_place_handle(setup)
        place = self.driver.title
        uid = self.db_utilities.getPlaceUniqueId(str(self.random_number))
        # search for element that is has wild in the last name and view
        if self.place.performSearchByElementAndView(NBSConstants.ADD_PLACE_LAST_NAME_WILD, 'lastname'):
            self.logger.info("*Place Search By Place Name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*test_place_inactive:place Search By last name FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")
            pytest.fail(f"Inactive place Search By last name FAILED")

        # inactive the place
        # inactive_place = self.driver.find_element(By.ID, "btnInactivateT").text
        inactive_place = self.driver.find_element(By.XPATH, '//*[@id="subSec1"]/tbody/tr/td[2]').text
        self.driver.find_element(By.ID, "btnInactivateT").click()
        time.sleep(3)
        self.driver.switch_to.alert.accept()
        time.sleep(3)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Place").click()
        # search for the inactivated ID to make sure it is not coming up in the results
        if self.place.performSearchByInactiveElement(NBSConstants.ADD_PLACE_LAST_NAME_WILD,
                                                     inactive_place, uid):
            self.logger.info("*test_place_inactive:Inactive place Search By last name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*test_place_inactive:Inactive place Search By last name FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")
            pytest.fail(f"Inactive place Search By last name FAILED")

        # close and quit driver
        time.sleep(2)
        self.driver.close()
        self.driver.quit()

    def teardownMethod(self):
        # self.db_utilities.close_db_connection()
        TestPlace.driver_globe = None
        self.driver.close()
        self.driver.quit()
