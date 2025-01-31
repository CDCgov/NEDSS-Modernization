from datetime import datetime

from selenium.webdriver.common.by import By

from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.data_entry.vaccination import Vaccination
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

import time
import pytest

import os


class TestVaccination:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    logger.info(random_number)
    patient_search_submit_id = 'Submit'
    db_utilities = DBUtilities()
    no_of_repeats = 2

    def setup_and_investigation_handle(self, setup):
        self.driver = setup
        TestVaccination.driver_globe = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.patient = PatientObject(self.driver)
        self.vaccination = Vaccination(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Open Investigations").click()
        time.sleep(1)

    def setup_and_patient_handle(self, setup):
        """

        :param setup:
        :return:
        """
        self.driver = setup
        TestVaccination.driver_globe = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.patient = PatientObject(self.driver)
        self.vaccination = Vaccination(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Patient").click()
        time.sleep(1)

    def close_and_quit_driver(self):
        """
        Close a d quit driver.
        :return:
        """
        self.driver.close()
        self.driver.quit()

    def test_add_vaccinations(self, setup):
        """
        Test for adding new vaccination.
        steps :
        1 - search patient
        2 - aad new patient
        3 - click add vaccination
        4 - submit vaccination

        :param setup: object,driver setup
        :return: boolean , return test status true or false
        """
        try:

            # get patient handle, search patient and add new patient
            self.setup_and_patient_handle(setup)
            self.vaccination.search_patientBy_lastName(NBSConstants.SEARCH_BY_LAST_NAME_VAL)
            self.patient.addNewPatient(self.random_number_for_patient)
            time.sleep(2)

            for i in range(0, self.no_of_repeats):
                self.patient.get_event_handle()
                time.sleep(2)

                # add
                self.patient.click_addNew_vaccinations()
                # vaccination child page
                self.vaccination.switch_to_current_window()
                self.driver.maximize_window()
                time.sleep(2)
                self.vaccination.click_vaccination_tab()
                time.sleep(2)
                self.vaccination.set_vaccination_admin()

                # get search view provider window
                time.sleep(2)
                self.vaccination.click_search_provider()
                self.vaccination.switch_to_current_window()
                # self.driver.maximize_window()
                time.sleep(1)
                self.vaccination.search_and_select_provider()
                time.sleep(1)

                # click on search organization button
                self.vaccination.switch_to_current_window()
                self.vaccination.click_search_org()

                # search the organization by last name
                self.vaccination.switch_to_current_window()
                # self.driver.maximize_window()
                time.sleep(1)
                # search_view_organization will search, click on add and will add details for new organization
                self.vaccination.search_view_organization()
                time.sleep(1)

                self.vaccination.switch_to_current_window()
                self.vaccination.set_vacc_administered_by_details(self.random_number)
                time.sleep(1)
                self.driver.find_element(By.NAME, "Submit").click()
                time.sleep(1)
                # close current window and back to original window
                self.driver.find_element(By.NAME, "Cancel").click()
                time.sleep(2)
                self.vaccination.switch_to_original_window()
                self.logger.info("test_add_vaccinations: Add vaccination test successful")
            assert True
        except Exception as e:
            self.logger.info("test_add_vaccinations: Add vaccination test failed")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_add_vaccinations.png")
            # self.driver.close()
            pytest.fail(f"add vaccinations test FAILED")

        # self.driver.close()
        # self.driver.quit()

    def test_edit_vaccination(self, setup):
        """
        search for patient that has been added
        navigates to vaccination tab
        click edit
        click submit
        close the window
        :return:
        """
        # get current window
        try:
            self.setup_and_patient_handle(setup)
            # current_window = self.driver.window_handles[-1]
            # self.driver.switch_to.window(current_window)
            time.sleep(2)
            # last_name = "LastName-011624224026"
            last_name = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(self.random_number_for_patient))
            self.vaccination.search_patientBy_lastName(last_name)
            time.sleep(1)
            if self.vaccination.view_patient():
                # click patient id and vaccination data
                self.vaccination.edit_and_set_vaccinationData()
                self.vaccination.switch_to_original_window()
                self.logger.info(last_name + "test_edit_vaccination: edit patient successful")
                assert True
            else:
                self.logger.info("test_edit_vaccination fail: Patient not found")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_edit_vaccination.png")
                pytest.fail(f"Edit vaccinations test FAILED : Patient not found")
        except Exception as e:
            self.logger.info("test_edit_vaccination: Edit vaccination test failed")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_edit_vaccination.png")
            # self.driver.close()
            pytest.fail(f"Edit vaccinations test FAILED - {str(e)}")

    # self.close_and_quit_driver()

    # Delete vaccination
    def test_delete_vaccination(self, setup):
        """
        Delete vaccinations
        :param setup: object, driver setup
        :return: None
        """
        try:
            self.setup_and_patient_handle(setup)
            time.sleep(2)
            # last_name = "LastName-011624224026"
            last_name = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(self.random_number_for_patient))
            self.vaccination.search_patientBy_lastName(last_name)
            time.sleep(1)
            if self.vaccination.view_patient():
                # click patient id and vaccination data
                self.vaccination.delete_vaccination()
                time.sleep(3)
                self.vaccination.switch_to_original_window()
                self.logger.info(last_name + "test_delete_vaccination: vaccination Deleted")
                assert True
            else:
                self.logger.info("test_delete_vaccination fail: vaccination not found")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_delete_vaccination.png")
                pytest.fail(f"Delete vaccinations test FAILED : Patient not found")
        except Exception as e:
            self.logger.info("test_delete_vaccination: Delete vaccination test failed")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_delete_vaccination.png")
            # self.driver.close()
            pytest.fail(f"Delete vaccinations test FAILED - {str(e)}")

    # Print vaccination
    def test_print_vaccination(self, setup):
        """
        Print vaccinations
        search patient that is added
        navigate to vaccination-> click
        navigate to vaccination tab
        click print
        :return: None
        """
        try:
            self.setup_and_patient_handle(setup)
            time.sleep(2)
            # last_name = "LastName-011624224026"
            last_name = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(self.random_number_for_patient))
            self.vaccination.search_patientBy_lastName(last_name)
            time.sleep(1)
            if self.vaccination.view_patient():
                # click patient id and vaccination data
                self.vaccination.print_vaccination()
                time.sleep(1)
                self.logger.info("test_print_vaccination pass: vaccination print page ")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_vaccination.png")
                assert True
            else:
                self.logger.info("test_print_vaccination fail: vaccination not printed")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_vaccination.png")
                pytest.fail(f"Print vaccinations test FAILED : vaccination not printed")
        except Exception as e:
            self.logger.info("test_print_vaccination: print vaccination test failed")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_vaccination.png")
            # self.driver.close()
            pytest.fail(f"Print vaccinations test FAILED - {str(e)}")
        time.sleep(4)
        self.close_and_quit_driver()
