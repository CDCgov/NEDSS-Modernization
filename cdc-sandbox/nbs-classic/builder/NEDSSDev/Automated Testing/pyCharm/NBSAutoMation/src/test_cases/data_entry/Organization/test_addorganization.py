from datetime import datetime
from selenium.webdriver.common.by import By
from src.page_objects.data_entry.organization import Organization
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

import time
import pytest

import os
import random


class TestOrganization:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    logger.info(random_number)
    db_utilities = DBUtilities()

    # search and add new organization

    def setup_and_org_handle(self, setup):
        self.driver = setup
        TestOrganization.driver_globe = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.organization = Organization(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Organization").click()
        time.sleep(2)

    def test_dataEntry_Org_New(self, setup):
        self.logger.info("****CreateNew_Organization****")
        self.setup_and_org_handle(setup)
        organization = self.driver.title

        if organization == NBSConstants.ORGANIZATION_SEARCH_SECTION_HEADER_LABEL:
            self.logger.info("**test_add_organization_to_nbs_system:NBS org Search Landing Page PASSED**")
            assert True
            self.organization.setOrgLastName(self.random_number)
            self.organization.setOrgSubmit()
            self.organization.setOrgAdd()
            add_org_header_title = self.organization.getAddOrganizationHeader()

            if add_org_header_title.strip() == NBSConstants.ADD_ORG_HEADER_TITLE:
                self.logger.info("**test_add_org_to_nbs_system:NBS Add org landing Page PASSED**")
                assert True
                time.sleep(1)
                try:
                    self.organization.setOrgAdminSection(self.random_number)
                    self.organization.setOrgIdentitySection(NBSConstants.ADD_PROVIDER_IDENTIFICATION_REPEAT,
                                                            str(self.random_number))
                    self.organization.setOrgAddressSection(NBSConstants.ADD_PROVIDER_IDENTIFICATION_REPEAT,
                                                           str(self.random_number))
                    time.sleep(4)
                    self.organization.setOrgTelephoneSection(NBSConstants.ADD_PROVIDER_IDENTIFICATION_REPEAT)
                    self.organization.addOrganizationToSystem()
                    time.sleep(4)
                    if self.driver.find_element(By.XPATH, "//span[@class='visible']").is_displayed():
                        OrgIdGenerated = self.driver.find_element(By.XPATH, "//span[@class='visible']").text
                        self.logger.info(OrgIdGenerated + " is created successfully")
                        assert True
                    else:
                        self.logger.info("Organization is not created successfully")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "test_CreateOrganization.png")
                        # self.driver.close()
                        pytest.fail(f"Add organization Test FAILED")

                except:
                    self.logger.info("Organization details already exist " + "OrganizationName" +
                                     " New organization is not created")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "test_CreateOrganization.png")
                    # self.driver.close()
                    pytest.fail(f"Add organization Test FAILED")

            else:
                self.logger.info("**test_add_org_to_nbs_system:NBS Add org Landing Page FAILED**")
                pytest.fail("test_add_org_to_nbs_system:NBS Add org Landing Page test failed.")

    # search newly added organization
    def test_search_Organization(self, setup):
        self.logger.info("****Search_Organization****")
        self.setup_and_org_handle(setup)
        uid = self.db_utilities.getOrgUniqueId(str(self.random_number))

        # search by last name
        if self.organization.performSearchByElement(NBSConstants.ADD_ORG_LAST_NAME,
                                                    self.random_number, uid):
            self.logger.info("*org Search By Last Name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*ORG Search By Last Name FAILED**")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "Org_search_by_ln.png")
            pytest.fail(f"Search By Last Name FAILED")
        # self.driver.quit()

    # Test case to edit by selecting 2nd radio button which creates a new organization (creates new organization with
    # lastname_random_number, lastname_12345)

    def test_edit_org_with_typographical_reason(self, setup):
        """
        Test method for editing the organization with typographical reason.
        """
        self.logger.info("****Search_Organization****")
        self.setup_and_org_handle(setup)
        # uid = self.db_utilities.getOrgUniqueId(str(self.random_number))
        # search by last name
        if self.organization.performSearchByElementAndExit(NBSConstants.ADD_ORG_LAST_NAME, self.random_number):
            self.logger.info("*org Search By Last Name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*ORG Search By Last Name FAILED**")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "Org_search_by_ln.png")
            pytest.fail(f"Search By Last Name FAILED")
        try:
            self.driver.find_element(By.LINK_TEXT, "View").click()
            time.sleep(3)
            self.driver.find_element(By.NAME, "Edit").click()
            time.sleep(2)
            radio_buttons = self.driver.find_elements(By.CSS_SELECTOR, "input[name='tba']")
            radio_buttons[NBSConstants.EDIT_ORG_RADIO_FIRST_OPTION].click()
            time.sleep(2)
            self.organization.setOrgAdminSection(self.random_number)
            time.sleep(3)
            # org_name = datetime.now().strftime("%d%m%y%H%M%S")
            self.organization.setOrgLastNameEdit(str(self.random_number))
            time.sleep(3)
            self.organization.editOrganizationIdActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            self.organization.editOrgAddressActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            time.sleep(2)
            self.organization.editOrgTelephoneActions(NBSConstants.EDIT_OPTION)
            self.driver.find_element(By.ID, "Submit").click()
            assert True
        except Exception as e:
            self.logger.info(str(e))
            pytest.fail(f"FAILED edit organization with typographical reason")

    def test_edit_org_with_non_typographical_reason(self, setup):
        """
        Test method for editing the organization with non-typographical reason.
        """
        self.logger.info("****Search_Organization****")
        self.setup_and_org_handle(setup)
        # uid = self.db_utilities.getOrgUniqueId(str(self.random_number))
        # search by last name
        if self.organization.performSearchByElementAndExit(NBSConstants.ADD_ORG_LAST_NAME, self.random_number):
            self.logger.info("*org Search By Last Name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*ORG Search By Last Name FAILED**")
            self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "Org_search_by_ln.png")
            pytest.fail(f"Search By Last Name FAILED")
        try:
            self.driver.find_element(By.LINK_TEXT, "View").click()
            time.sleep(3)
            self.driver.find_element(By.NAME, "Edit").click()
            time.sleep(2)
            radio_buttons = self.driver.find_elements(By.CSS_SELECTOR, "input[name='tba']")
            radio_buttons[NBSConstants.EDIT_ORG_RADIO_SEC_OPTION].click()
            time.sleep(2)
            self.organization.setOrgAdminSection(self.random_number)
            time.sleep(3)
            org_name = 'lastname_' + str(self.random_number)
            self.organization.setOrgLastNameEdit(org_name)
            time.sleep(3)
            self.organization.editOrganizationIdActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            self.organization.editOrgAddressActions(NBSConstants.EDIT_OPTION, str(self.random_number))
            time.sleep(2)
            self.organization.editOrgTelephoneActions(NBSConstants.EDIT_OPTION)
            time.sleep(3)
            self.driver.find_element(By.ID, "Submit").click()
            time.sleep(3)
            self.driver.switch_to.alert.accept()
            time.sleep(2)
            random_quick_code = str(datetime.now().strftime("%d%m%y%H%M%S"))
            self.organization.setOrganizationQuickCode(random_quick_code[:10])
            time.sleep(2)
            self.driver.find_element(By.ID, "Submit").click()
            assert True
        except Exception as e:
            self.logger.info(str(e))
            pytest.fail(f"FAILED to edit organization with non-typographical reason.")

    # Test case to view and inactivate org created by selecting 2nd radio button
    def test_org_inactive(self, setup):
        """ Test case for inactivating organization """
        self.logger.info("****Search_Organization****")
        self.setup_and_org_handle(setup)
        uid = self.db_utilities.getOrgUniqueId(str(self.random_number))

        # search for element that is has wild in the last name and view
        if self.organization.performSearchByLastname(NBSConstants.ADD_ORG_LAST_NAME_WILD,
                                                     self.random_number, uid):
            self.logger.info("*test_org_inactive:Organization Search By last name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*test_org_inactive:Organization Search By last name FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")
            pytest.fail(f"Inactive Organization Search By last name FAILED")

        # inactive the organization
        inactive_org = self.driver.find_element(By.ID, "name.nmTxt").text
        self.driver.find_element(By.ID, "Inactivate").click()
        time.sleep(3)
        self.driver.switch_to.alert.accept()
        time.sleep(3)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Organization").click()
        # search for the inactivated ID to make sure it is not coming up in the results
        if self.organization.performSearchByInactiveElement(NBSConstants.ADD_ORG_LAST_NAME_WILD,
                                                                inactive_org, uid):
            self.logger.info("*test_org_inactive:Inactive Organization Search By last name PASSED*")
            assert True
        else:
            # self.driver.close()
            self.logger.info("*test_org_inactive:Inactive Organization Search By last name FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")
            pytest.fail(f"Inactive Organization Search By last name FAILED")

        # close and quit driver
        time.sleep(2)
        self.driver.close()
        self.driver.quit()

    def tearDown(self):
        # self.organization.closeDBConnection()
        TestOrganization.driver_globe = None
        self.driver.close()
        self.driver.quit()
