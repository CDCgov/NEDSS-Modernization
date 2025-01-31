class NBSConstants:
    # define constants for NBS Application
    # Base Page Object
    # define constants for NBS Application
    # Base Page Object
    TYPE_CODE_ID = "ID"
    TYPE_CODE_XPATH = "XPATH"
    TYPE_CODE_LINK_TEXT = "LINK_TEXT"
    TYPE_CODE_NAME = "NAME"
    TYPE_CODE_CSS = "CSS"
    WAITING_TIME_IN_SECONDS = 1
    ENABLE_WAITING_TIME = "YES"
    HTML_TEXT_BOX_ELEMENT = "TEXTBOX"
    HTML_DROP_DOWN_ELEMENT = "DROPDOWN"
    HTML_SET_DROP_DOWN_RANDOM_VALUE = "DROPDOWN-RANDOM"
    HTML_DATE_ICON_ELEMENT = "DATE-ICON"
    # ENABLE_WAITING_TIME = "DEMO"

    # append text to html elements for search through nbs site for automation
    APPEND_TEST_FOR_LAST_NAME = "LastName"
    MERGE_LAST_NAME = "Manual_Merge"
    APPEND_TEST_FOR_QUEUE_NAME = "Custom_queue"
    APPEND_TEST_FOR_SECOND_LAST_NAME = "SecondLastName"
    APPEND_TEST_FOR_FIRST_NAME = "FirstName"
    MERGE_FIRST_NAME = "FirstName"
    MERGE_MIDDLE_NAME = "MiddleName"
    SYSTEM_MERGE_LAST_NAME = "System_Identi_Merge"
    SYSTEM_MERGE_FIRST_NAME = "FirstName"
    SYSTEM_MERGE_MIDDLE_NAME = "MiddleName"
    APPEND_TEST_FOR_MIDDLE_NAME = "MiddleName"
    APPEND_TEST_FOR_MIDDLE_NAME_SECOND = "SecondMiddleName"
    APPEND_TEST_FOR_STREET_ADDR1 = "address1-"
    APPEND_TEST_FOR_STREET_ADDR2 = "address2-"
    APPEND_TEST_FOR_STREET_CITY = "City-"
    ADD_PATIENT_LAST_NAME_WILD = "lnwild"
    

    # sections
    IDENTIFICATION_SECTION = "Identification"
    ADDRESS_SECTION = "Address"
    TELE_PHONE_SECTION = "Telephone"
    NAME_SECTION = "Name"
    RACE_SECTION = "Race"
    ETHNICITY_SECTION = "Ethnicity"
    SEX_BIRTH_SECTION = "SexAndBirth"
    MORTALITY_SECTION = "Mortality"
    GENERAL_INFORMATION_SECTION = "General"
    PROVIDER_DRUG_SECTION = "SUSCEPTIBILITY"

    # generate random date
    START_DATE_YEAR = 1960
    START_DATE_MONTH = 1
    START_DATE_DAY = 1
    END_DATE_YEAR = 2023
    END_DATE_MONTH = 1
    END_DATE_DAY = 1

    # Calendar Locators
    CALENDAR_TODAY_DATE = "cpTodayText"
    CALENDAR_DISPLAY_YEAR = "//span[@class='cpYearNavigation']"
    CALENDAR_DISPLAY_MONTH = "//span[@class='cpMonthNavigation']"
    CALENDAR_GET_ALL_DAYS = "//a[@class='cpCurrentMonthDate']"
    CALENDAR_PRE_MONTH_ARROW = "//a[@class='cpMonthNavigation']"
    CALENDAR_PRE_YEAR_ARROW = "//a[@class='cpYearNavigation']"

    # Enable Delete Button Validation
    VALIDATE_DELETE_BUTTON = "NO"
    LOG_OUT_FROM_PATIENT_SCREEN = "NO"

    # provider
    PROVIDER_SEARCH_PAGE_TITLE = "Find Provider"
    PROVIDER_SEARCH_SECTION_HEADER_LABEL = "Provider Search Criteria"
    PROVIDER_SEARCH_CONTAINS_OPERATOR = "CT"
    PROVIDER_SEARCH_EQUAL_OPERATOR = "="
    PROVIDER_SEARCH_NOT_EQUAL_OPERATOR = "!="
    PROVIDER_SEARCH_SOUNDS_LIKE_OPERATOR = "SL"
    PROVIDER_SEARCH_STARTS_WITH_OPERATOR = "SW"
    PROVIDER_LAST_NAME_SEARCH_PAGE_ID = "providerSearch.lastName"
    PROVIDER_FIRST_NAME_SEARCH_PAGE_ID = "providerSearch.firstName"
    PROVIDER_ADDRESS_1_SEARCH_PAGE_ID = "providerSearch.streetAddr1"
    PROVIDER_CITY_SEARCH_PAGE_ID = "providerSearch.cityDescTxt"
    PROVIDER_STATE_SEARCH_PAGE_ID = "providerSearch.state"
    PROVIDER_POSTAL_SEARCH_PAGE_ID = "providerSearch.zipCd"
    PROVIDER_PHONE_SEARCH_PAGE_ID = "providerSearch.phoneNbrTxt"
    PROVIDER_IDENT_TYPE_SEARCH_PAGE_ID = "//*[@id='providerSearch.typeCd']"
    PROVIDER_IDENT_VALUE_SEARCH_PAGE_ID = "//*[@id='providerSearch.rootExtensionTxt']"
    ADD_PROVIDER_HEADER_TITLE = "Add Provider"
    ADD_PROVIDER_GENERAL_COMMENTS = "General Comments"
    ADD_PROVIDER_ADDRESS_COMMENTS = "Address Comments"
    ADD_PROVIDER_LOCATOR_COMMENTS = "Telephone Comments"
    ADD_PROVIDER_LAST_NAME = "LastName"
    ADD_ORG_LAST_NAME = "LastName"
    ADD_PROVIDER_FIRST_NAME = "FirstName"
    ADD_PROVIDER_MIDDLE_NAME = "MiddleName"
    ADD_PROVIDER_STREET_ADDRESS = "address1"
    ADD_PROVIDER_CITY_NAME = "City"
    ADD_ORG_CITY_NAME = "City"
    ADD_PROVIDER_STATE_CODE = "state"
    ADD_PROVIDER_POSTAL_CODE = "postal"
    ADD_PROVIDER_TEL_PHONE = "Telephone"
    ADD_PROVIDER_IDENT_SELECT = "Identification"
    ADD_PROVIDER_LAST_NAME_WILD = "LastNameWild"
    ADD_PROVIDER_REFINE_SEARCH = "RefineSearch"
    ADD_PROVIDER_PAGINATION_VALIDATION = "pagination"
    ADD_NEW_PROVIDER_TO_THE_SYSTEM = "ADD"
    VIEW_ADDED_PROVIDER_TO_THE_SYSTEM = "VIEW"
    EDIT_ADDED_PROVIDER_TO_THE_SYSTEM = "EDIT"
    PROVIDER_IDENTIFICATION_SECTION = "Identification"
    PROVIDER_ADDRESS_SECTION = "Address"
    PROVIDER_PHONE_SECTION = "Telephone"
    NBS_HOME_PAGE = "Home"
    NBS_DATA_ENTRY_MENU = "Data Entry"
    NBS_DATA_ENTRY_PROVIDER_MENU = "Provider"
    PROVIDER_SEARCH_SUBMIT_ID = "Submit"
    ADD_NUMBER_OF_REPEATABLE_SECTIONS = 2
    EDIT_RECORD_NUM_IN_SECTION = 1
    DELETE_RECORD_NUM_IN_SECTION = 2
    ADD_PROVIDER_IDENTIFICATION_REPEAT = 4
    ORGANIZATION_SEARCH_SECTION_HEADER_LABEL = "Organization Search Criteria"
    ADD_ORG_HEADER_TITLE = "Add Organization"
    ADD_PLACE_HEADER_TITLE = "Add Place"
    EDIT_ORG_HEADER_TITLE = "Edit Organization"
    ADD_ORG_ADDRESS_COMMENTS = "Address Comments"
    EDIT_OPTION = 1
    PLACE_SEARCH_SECTION_HEADER_LABEL = "Find Place"
    EDIT_ORG_RADIO_FIRST_OPTION = 0
    EDIT_ORG_RADIO_SEC_OPTION = 1
    ADD_ORG_LAST_NAME_WILD = "lastname"
    # DEFAULT_COUNTRY_CODE = "186"
    ADD_PLACE_LAST_NAME = "LastName"
    ADD_PLACE = "PlaceName"
    EDIT_PLACE_RADIO_FIRST_OPTION = 0
    EDIT_PLACE_RADIO_SECOND_OPTION = 1
    ADD_PLACE_LAST_NAME_WILD = "lastname"
    ADD_NEW_OPTION = 3
    SEARCH_BY_LAST_NAME_VAL = '%'
