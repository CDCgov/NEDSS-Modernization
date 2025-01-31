import os
import time
from datetime import datetime

import pytest

from src.page_objects.data_entry.provider import ProviderObject
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

class TestProvider:
    db_utilities = DBUtilities()
    testcase_start_time = None
    provider_uid = None
    randon_number_add_provider = None
    logger = LogUtils.loggen(__name__)
    logger.info(randon_number_add_provider)

    # Testcase for provider search submit button
    @pytest.mark.run(order=1)
    def test_add_provider_to_nbs_system(self, setup):
        TestProvider.testcase_start_time = int(time.time() * 1000)
        self.driver = setup
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            TestProvider.randon_number_add_provider = datetime.now().strftime("%m%d%y%H%M%S")
            self.logger.info("Random Number for Provider Creation" + str(TestProvider.randon_number_add_provider))
            self.provider = ProviderObject(self.driver)
            # self.navigateToProviderLandingPage(self.provider, self.driver)
            self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                     NBSConstants.WAITING_TIME_IN_SECONDS)
            self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_DATA_ENTRY_MENU)
            self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                     NBSConstants.WAITING_TIME_IN_SECONDS)
            self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                             NBSConstants.NBS_DATA_ENTRY_PROVIDER_MENU)

            if self.driver.title == ProviderObject.provider_search_section_header_label:
                self.logger.info("**test_add_edit_provider_to_nbs_system:NBS Provider Search Landing Page PASSED**")
                # assert True
                self.provider.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                                     ProviderObject.provider_last_name_search_page_id,
                                                     ProviderObject.add_provider_last_name.__add__("-").__add__(
                                                         str(TestProvider.randon_number_add_provider)))
                self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                         NBSConstants.WAITING_TIME_IN_SECONDS)
                self.provider.performButtonClick(NBSConstants.TYPE_CODE_ID, ProviderObject.provider_search_submit_id)
                self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                         NBSConstants.WAITING_TIME_IN_SECONDS)
                self.provider.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='Add']")
                add_provider_header_title = self.provider.getAddProviderHeader()
                self.logger.info(add_provider_header_title)
                if add_provider_header_title.strip() == 'Add Provider':
                    self.logger.info(
                        "**test_add_edit_provider_to_nbs_system:NBS Add Provider landing Page PASSED**")
                    # assert True
                    self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                             NBSConstants.WAITING_TIME_IN_SECONDS)
                    try:
                        if self.provider.addNewProvider(TestProvider.randon_number_add_provider):
                            self.logger.info(
                                "*test_add_edit_provider_to_nbs_system:NBS Add Provider Created Test PASSED*")
                            assert True
                        else:
                            self.logger.info(
                                "**test_add_edit_provider_to_nbs_system:NBS Add Provider Created Test FAILED**")
                            self.driver.save_screenshot(
                                os.path.abspath(os.curdir) + "\\screenshots\\" + "add_provider.png")
                            self.driver.close()
                    except:
                        self.logger.info("Error while creating Provider")

                    self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
                    self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
                else:
                    self.logger.info("**test_add_provider_to_nbs_system:NBS Add Provider Landing Page FAILED**")
                    self.driver.close()
                    pytest.fail("test_add_provider_to_nbs_system:NBS Add Provider Landing Page test failed.")

            else:
                self.logger.info("**test_add_provider_to_nbs_system:NBS Provider Search Landing Page FAILED**")
                self.driver.close()
                pytest.fail("test_add_provider_to_nbs_system:NBS Provider Search Landing Page test failed.")

    @pytest.mark.run(order=2)
    def test_edit_same_provider_to_nbs_system(self, setup):
        self.driver = setup
        self.provider = ProviderObject(self.driver)
        # Since Provider is Created, Get Values related to Provider
        try:

            # get values related to created provider from DB
            TestProvider.provider_uid = self.db_utilities.getProviderUniqueId(
                "LastName-".__add__(str(TestProvider.randon_number_add_provider)),
                "FirstName-".__add__(str(TestProvider.randon_number_add_provider))
            )
            self.logger.info(TestProvider.provider_uid)
            if TestProvider.provider_uid is not None:
                self.navigateToProviderLandingPage(self.provider, self.driver, TestProvider.provider_uid)
        except:
            self.logger.info("***Error while connecting and getting db values***")
        TestProvider.randon_number_add_provider = datetime.now().strftime("%m%d%y%H%M%S")
        if self.provider.performEditProvider(TestProvider.randon_number_add_provider):
            self.logger.info("*test_add_edit_provider_to_nbs_system:NBS Edit Same Provider Test PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_edit_same_provider_to_nbs_system:NBS Edit Same Provider Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "edit_provider.png")
            self.driver.close()
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)

    @pytest.mark.run(order=3)
    def test_create_new_provider_with_edit_screen(self, setup):
        self.driver = setup
        self.provider = ProviderObject(self.driver)
        self.navigateToProviderLandingPage(self.provider, self.driver, TestProvider.provider_uid)
        if self.provider.createNewProviderFromExistingProvider(TestProvider.randon_number_add_provider):
            self.logger.info("*test_create_new_provider_with_edit_screen:Create New Provider From Edit Screen Test "
                             "PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_create_new_provider_with_edit_screen:Create New Provider From Edit Screen Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "edit_new_provider.png")
            self.driver.close()

        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)

    @pytest.mark.run(order=4)
    def test_search_provider_from_nbs_system(self, setup):
        city_state_code = {}
        prov_telephone = {}
        prov_identification = {}
        self.driver = setup
        self.provider = ProviderObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_DATA_ENTRY_MENU)
            self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_DATA_ENTRY_PROVIDER_MENU)
            if self.driver.title.strip() == ProviderObject.provider_search_section_header_label.strip():
                try:
                    city_state_code = self.db_utilities.getProviderCityStatePostal(TestProvider.provider_uid)
                    prov_telephone = self.db_utilities.getProviderContactTelephone(TestProvider.provider_uid)
                    prov_identification = self.db_utilities.getProviderIdentificationRecord(TestProvider.provider_uid)
                except:
                    self.logger.info("error while address for test_search_provider_from_nbs_system from DB")

                # search by last name
                if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                        ProviderObject.provider_last_name_search_page_id,
                                                        ProviderObject.add_provider_last_name,
                                                        TestProvider.randon_number_add_provider,
                                                        TestProvider.provider_uid):
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Last Name PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Last Name FAILED**")
                    self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_ln.png")

                # search by first name
                if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                        ProviderObject.provider_first_name_search_page_id,
                                                        ProviderObject.add_provider_first_name,
                                                        TestProvider.randon_number_add_provider,
                                                        TestProvider.provider_uid):
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By First Name PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By First Name FAILED**")
                    self.driver.save_screenshot(os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_fn.png")

                # search by street address
                if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                        ProviderObject.provider_address_1_search_page_id,
                                                        ProviderObject.add_provider_street_address,
                                                        TestProvider.randon_number_add_provider,
                                                        TestProvider.provider_uid):
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By ADDRESS PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Search By First Name FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_address.png")

                # search by city
                if city_state_code.get("city"):
                    if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                            ProviderObject.provider_city_search_page_id,
                                                            ProviderObject.add_provider_city_name,
                                                            city_state_code.get("city"),
                                                            TestProvider.provider_uid):
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By City Name PASSED*")
                        assert True
                    else:
                        self.driver.close()
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By City Name FAILED**")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_city.png")

                # search by state
                if city_state_code.get("state"):
                    if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                            ProviderObject.provider_state_search_page_id,
                                                            ProviderObject.add_provider_state_code,
                                                            city_state_code.get("state"),
                                                            TestProvider.provider_uid):
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By State PASSED*")
                        assert True
                    else:
                        self.driver.close()
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By State FAILED**")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_state.png")

                # search by postal code
                if city_state_code.get("postal"):
                    if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                            ProviderObject.provider_postal_search_page_id,
                                                            ProviderObject.add_provider_postal_code,
                                                            city_state_code.get("postal"),
                                                            TestProvider.provider_uid):
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Zip Code PASSED*")
                        assert True
                    else:
                        self.driver.close()
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Zip Code FAILED**")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_zip.png")

                # search by phone number
                if prov_telephone.get("telephone"):
                    if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                            ProviderObject.provider_phone_search_page_id,
                                                            ProviderObject.add_provider_tel_phone,
                                                            prov_telephone.get("telephone"),
                                                            TestProvider.provider_uid):
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By PHONE PASSED*")
                        assert True
                    else:
                        self.driver.close()
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By PHONE FAILED**")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_phone.png")

                # search by identification type
                if prov_identification.get("identi_select"):
                    if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_XPATH,
                                                            None,
                                                            ProviderObject.add_provider_ident_select,
                                                            prov_identification, TestProvider.provider_uid):
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Identification "
                                         "PASSED*")
                        assert True
                    else:
                        self.driver.close()
                        self.logger.info("*test_search_provider_from_nbs_system:Provider Search By Identification "
                                         "FAILED**")
                        self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_ident.png")

                # pagination validation on search results
                if self.provider.validatePaginationOnResults():
                    self.logger.info("*test_search_provider_from_nbs_system:Pagination on Search Results PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_search_provider_from_nbs_system:Pagination on Search Results FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_pg.png")

                # find created provider with wildcard search validation
                if self.provider.performSearchByElement(NBSConstants.TYPE_CODE_ID,
                                                        ProviderObject.provider_last_name_search_page_id,
                                                        ProviderObject.add_provider_last_name_wild,
                                                        TestProvider.randon_number_add_provider,
                                                        TestProvider.provider_uid):
                    self.logger.info("*test_search_provider_from_nbs_system:Provider wildcard Search By last name "
                                     "PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_search_provider_from_nbs_system:Provider wildcard Search By last name "
                                     "FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")

                # Validate Refine search on provider search results page

                if self.provider.performRefineSearchValidation(TestProvider.randon_number_add_provider, city_state_code,
                                                               prov_telephone, prov_identification):
                    self.logger.info("*test_search_provider_from_nbs_system:Provider Refine Search By all fields "
                                     "PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info(
                        "*test_search_provider_from_nbs_system:Provider Refine Search By all fields FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_refine_search.png")

        self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    @pytest.mark.run(order=5)
    def test_view_and_inactive_nbs_provider(self, setup):
        self.driver = setup
        self.provider = ProviderObject(self.driver)
        self.navigateToProviderLandingPage(self.provider, self.driver, TestProvider.provider_uid)
        # verify details link on the View provider page
        self.provider.validateDetailsLink()
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)
        # make  provider inactive on the View provider page
        if self.provider.makeProviderInactive():
            self.logger.info("*test_view_and_inactive_nbs_provider:InActive Provider Test PASSED*")
            assert True
        else:
            self.logger.info(
                "*test_view_and_inactive_nbs_provider:InActive Provider Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "provider_inactive.png")
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)
        self.navigateToProvider(self.provider, self.driver)
        element = None
        try:
            element = self.provider.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                "//a[contains(@href, "
                                                "'uid=".__add__(str(TestProvider.provider_uid)) + "')]")
        except:
            element = None

        if element is not None:
            self.logger.info(
                "*test_view_and_inactive_nbs_provider:Provider Inactive TEST FAILED")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "provider_inactive.png")

        self.provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lp = LoginPage(self.driver)
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lp.logoutFromNBSApplication()
        self.provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                 NBSConstants.WAITING_TIME_IN_SECONDS)
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestProvider.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()

    def navigateToProviderLandingPage(self, provider, driver, uid):
        self.navigateToProvider(provider, driver)
        try:
            provider.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                        "//a[contains(@href, "
                                        "'uid=".__add__(str(uid)) + "')]")

        except:
            self.logger.info("Could Not Found The Specified Record")
        provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def navigateToProvider(self, provider, driver):
        provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_DATA_ENTRY_MENU)
        provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        provider.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_DATA_ENTRY_PROVIDER_MENU)
        if driver.title.strip() == ProviderObject.provider_search_section_header_label.strip():
            provider.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                            ProviderObject.provider_last_name_search_page_id,
                                            "LastName-".__add__(str(TestProvider.randon_number_add_provider)))
            provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            provider.performButtonClick(NBSConstants.TYPE_CODE_ID, ProviderObject.provider_search_submit_id)
            provider.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            self.logger.info("**navigateToProviderLandingPage:NBS View Provider Landing Page FAILED**")
            driver.close()
