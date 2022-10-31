/// <reference types="cypress" />

import CodedResult from '../models/enums/CodedResult';
import { CollectionSite } from '../models/enums/CollectionSite';
import { Deceased } from '../models/enums/Deceased';
import { Ethnicity } from '../models/enums/Ethnicity';
import { MaritalStatus } from '../models/enums/MaritalStatus';
import { PatientIdentificationType } from '../models/enums/PatientIdentificationType';
import { ProgramArea } from '../models/enums/ProgramArea';
import { Race } from '../models/enums/Race';
import { Sex } from '../models/enums/Sex';
import { SpecimenSource } from '../models/enums/SpecimenSource';
import { State } from '../models/enums/State';
import { Suffix } from '../models/enums/Suffix';
import Test from '../models/enums/Test';
import { PatientStatus } from '../models/LabReport';
import Organization from '../models/Organization';
import DateUtil from '../utils/DateUtil';
import BasePage from './BasePage';

enum Selector {
    // ****** Patient Tab ******
    PATIENT_TAB = 'td[id=tabs0head0]',

    // General Information
    PATIENT_COMMENTS = 'input[id=DEM196]',

    // Name information
    FIRST_NAME = 'input[id=DEM104]',
    MIDDLE_NAME = 'input[id=DEM105]',
    LAST_NAME = 'input[id=DEM102]',
    SUFFIX = 'input[name=DEM107_textbox]',

    // Other personal details
    DOB = 'input[id=DEM115]',
    CURRENT_SEX = 'input[name=DEM113_textbox]',
    DECEASED = 'input[name=DEM127_textbox]',
    MARITAL_STATUS = 'input[name=DEM140_textbox]',
    SSN = 'input[id=DEM133]',

    // Id information
    ID_AS_OF = 'input[id=NBS452]',
    ID_TYPE = 'input[name=DEM144_textbox]',
    ID_AUTHORITY = 'input[name=DEM146_textbox]',
    ID_VALUE = 'input[id=DEM147]',
    ID_ADD_BUTTON = 'tr[id=AddButtonToggleENTITYID100] td input',

    // Reporting address for case counting

    STREET_ADDRESS_1 = 'input[id=DEM159]',
    STREET_ADDRESS_2 = 'input[id=DEM160]',
    CITY = 'input[id=DEM161]',
    STATE = 'input[name=DEM162_textbox]',
    ZIP = 'input[id=DEM163]',
    COUNTY = 'input[name=DEM165_textbox]',
    COUNTRY = 'input[name=DEM167_textbox]',

    // Telephone information
    HOME_PHONE = 'input[id=DEM177]',
    WORK_PHONE = 'input[id=NBS002]',
    WORK_PHONE_EXT = 'input[id=NBS003]',
    CELL_PHONE = 'input[id=NBS006]',
    EMAIL = 'input[id=DEM182]',

    // Ethnicity and race info
    ETHNICITY = 'input[name=DEM155_textbox]',
    RACE_AMERICAN_INDIAN_OR_ALASKA_NATIVE = 'input[name=pageClientVO\\.americanIndianAlskanRace]',
    RACE_ASIAN = 'input[name=pageClientVO\\.asianRace]',
    RACE_BLACK_OR_AFRICAN_AMERICAN = 'input[name=pageClientVO\\.africanAmericanRace]',
    RACE_NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER = 'input[name=pageClientVO\\.hawaiianRace]',
    RACE_WHITE = 'input[name=pageClientVO\\.whiteRace]',
    RACE_OTHER = 'input[name=pageClientVO\\.otherRace]',
    RACE_REFUSED_TO_ANSWER = 'input[name=pageClientVO\\.refusedToAnswer]',
    RACE_NOT_ASKED = 'input[name=pageClientVO\\.notAsked]',
    RACE_UNKNOWN = 'input[name=pageClientVO\\.unKnownRace]',

    // ****** Lab report tab ******
    LAB_REPORT_TAB = 'td[id=tabs0head1]',

    // Facility and provider information
    REPORTING_FACILITY_QUICK_CODE = 'input[id=NBS_LAB365Text]',
    REPORTING_FACILITY_QUICK_CODE_LOOKUP_BUTTON = 'input[id=NBS_LAB365CodeLookupButton]',
    ORDERING_FACILITY_QUICK_CODE = 'input[id=NBS_LAB367Text]',
    ORDERING_FACILITY_QUICK_CODE_LOOKUP_BUTTON = 'input[id=NBS_LAB367CodeLookupButton]',
    ORDERING_PROVIDER_QUICK_CODE = 'input[id=NBS_LAB366Text]',
    ORDERING_PROVIDER_QUICK_CODE_LOOKUP_BUTTON = 'input[id=NBS_LAB366CodeLookupButton]',

    // Order details
    PROGRAM_AREA = 'input[name=INV108_textbox]',
    JURISDICTION = 'input[name=INV107_textbox]',
    SHARED = 'input[name=pageClientVO\\.answer(NBS012)]',
    LAB_REPORT_DATE = 'input[id=NBS_LAB197]',
    DATE_RECEIVED = 'input[id=NBS_LAB201]',

    // Ordered Test
    ORDERED_TEST = 'input[name=NBS_LAB112_textbox]',
    ACCESSION_NUMBER = 'input[id=LAB125]',
    SPECIMEN_SOURCE = 'input[name=LAB165_textbox]',
    SPECIMEN_SITE = 'input[name=NBS_LAB166_textbox]',
    SPECIMEN_COLLECTION_DATE = 'input[id=LAB163]',
    PATIENT_STATUS_AT_COLLECTION = 'input[name=NBS_LAB330_textbox]',

    // Resulted Test
    RESULTED_TEST = 'input[name=NBS_LAB220_textbox]',
    CODED_RESULT = 'input[name=NBS_LAB280_textbox]',
    NUMERIC_RESULTS = 'input[id=NBS_LAB364]',
    TEXT_RESULTS = 'textarea[id=NBS_LAB208]',
    REFERENCE_RANGE_FROM = 'input[id=NBS_LAB119]',
    REFERENCE_RANGE_TO = 'input[id=NBS_LAB120]',
    ADD_RESULTED_TEST_BUTTON = 'tr[id=AddButtonToggleRESULTED_TEST_CONTAINER] input[type=button]',
    RESULT_STATUS = 'input[name=NBS_LAB207_textbox]',
    RESULT_COMMENTS = 'textarea[id=NBS_LAB104]',

    // Lab report comments
    LAB_REPORT_COMMENTS = 'textarea[id=NBS460]',

    // General
    SUBMIT_BUTTON = 'input[name=Submit]'
}
export default class AddLabReportPage extends BasePage {
    activeTab: 'Patient' | 'LabReport' = 'Patient';

    constructor() {
        super('/MyTaskList1.do?ContextAction=AddLabDataEntry');
    }

    setActiveTab(activeTab: 'Patient' | 'LabReport'): Cypress.Chainable {
        this.activeTab = activeTab;
        if (activeTab === 'Patient') {
            return this.clickFirst(Selector.PATIENT_TAB)
                .get(Selector.FIRST_NAME, this.defaultOptions)
                .should('be.visible');
        } else {
            return this.clickFirst(Selector.LAB_REPORT_TAB)
                .get(Selector.REPORTING_FACILITY_QUICK_CODE, this.defaultOptions)
                .should('be.visible');
        }
    }

    // Patient tab section
    setPatientComments(comments: string): void {
        this.setText(Selector.PATIENT_COMMENTS, comments);
    }

    setFirstName(firstName: string): void {
        this.setText(Selector.FIRST_NAME, firstName);
    }

    setMiddleName(middleName: string): void {
        this.setText(Selector.MIDDLE_NAME, middleName);
    }

    setLastName(lastName: string): void {
        this.setText(Selector.LAST_NAME, lastName);
    }

    setSuffix(suffix: Suffix): void {
        this.setText(Selector.SUFFIX, suffix);
    }

    setDob(dob: Date): void {
        const dateString = DateUtil.getNBSFormattedDate(dob, false);
        this.setText(Selector.DOB, dateString);
    }

    setCurrentSex(sex: Sex): void {
        this.setText(Selector.CURRENT_SEX, sex);
    }

    setDeceased(deceased: Deceased): void {
        this.setText(Selector.DECEASED, deceased);
    }

    setMaritalStatus(maritalStatus: MaritalStatus): void {
        this.setText(Selector.MARITAL_STATUS, maritalStatus);
    }

    setSsn(ssn: string): void {
        this.setText(Selector.SSN, ssn);
    }

    setIdAsOf(date: Date): void {
        this.setText(Selector.ID_AS_OF, DateUtil.getNBSFormattedDate(date, false));
    }

    setIdType(idType: PatientIdentificationType): void {
        this.setText(Selector.ID_TYPE, idType);
    }

    setIdAuthority(authority: string): void {
        this.setText(Selector.ID_AUTHORITY, authority);
    }

    setIdValue(value: string): void {
        this.setText(Selector.ID_VALUE, value);
    }

    clickAddIdButton(): void {
        this.click(Selector.ID_ADD_BUTTON);
    }

    setStreetAddress1(address: string): void {
        this.setText(Selector.STREET_ADDRESS_1, address);
    }

    setStreetAddress2(address: string): void {
        this.setText(Selector.STREET_ADDRESS_2, address);
    }

    setCity(city: string): void {
        this.setText(Selector.CITY, city);
    }

    setState(state: State): void {
        this.setText(Selector.STATE, state);
    }

    setZip(zip: string): void {
        this.setText(Selector.ZIP, zip);
    }

    setCounty(county: string): void {
        this.setText(Selector.COUNTY, county);
    }

    setCountry(country: string): void {
        this.setText(Selector.COUNTRY, country);
    }

    setHomePhone(homePhone: string): void {
        this.setText(Selector.HOME_PHONE, homePhone);
    }

    setWorkPhone(workPhone: string): void {
        this.setText(Selector.WORK_PHONE, workPhone);
    }

    setWorkPhoneExtension(extension: string): void {
        this.setText(Selector.WORK_PHONE_EXT, extension);
    }

    setCellPhone(cellPhone: string): void {
        this.setText(Selector.CELL_PHONE, cellPhone);
    }

    setEmail(email: string): void {
        this.setText(Selector.EMAIL, email);
    }

    setEthnicity(ethnicity: Ethnicity): void {
        this.setText(Selector.ETHNICITY, ethnicity);
    }

    private clearRaceCheckboxes(): void {
        const raceCheckBoxes = [
            Selector.RACE_AMERICAN_INDIAN_OR_ALASKA_NATIVE,
            Selector.RACE_ASIAN,
            Selector.RACE_BLACK_OR_AFRICAN_AMERICAN,
            Selector.RACE_NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER,
            Selector.RACE_WHITE,
            Selector.RACE_OTHER,
            Selector.RACE_REFUSED_TO_ANSWER,
            Selector.RACE_NOT_ASKED,
            Selector.RACE_UNKNOWN
        ];
        raceCheckBoxes.forEach((cb) => this.setChecked(cb, false));
    }

    setRaces(races: Race[]) {
        this.clearRaceCheckboxes();

        races.forEach((race) => {
            switch (race) {
                case Race.AMERICAN_INDIAN_OR_ALASKA_NATIVE:
                    this.setChecked(Selector.RACE_AMERICAN_INDIAN_OR_ALASKA_NATIVE, true);
                    break;
                case Race.ASIAN:
                    this.setChecked(Selector.RACE_ASIAN, true);
                    break;
                case Race.BLACK_OR_AFRICAN_AMERICAN:
                    this.setChecked(Selector.RACE_BLACK_OR_AFRICAN_AMERICAN, true);
                    break;
                case Race.NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER:
                    this.setChecked(Selector.RACE_NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER, true);
                    break;
                case Race.NOT_ASKED:
                    this.setChecked(Selector.RACE_NOT_ASKED, true);
                    break;
                case Race.OTHER:
                    this.setChecked(Selector.RACE_OTHER, true);
                    break;
                case Race.REFUSED_TO_ANSWER:
                    this.setChecked(Selector.RACE_REFUSED_TO_ANSWER, true);
                    break;
                case Race.UNKNOWN:
                    this.setChecked(Selector.RACE_UNKNOWN, true);
                    break;
                case Race.WHITE:
                    this.setChecked(Selector.RACE_WHITE, true);
                    break;
            }
        });
    }

    // Lab report section

    setReportingFacility(reportingFacility: Organization): void {
        this.setText(Selector.REPORTING_FACILITY_QUICK_CODE, reportingFacility.quickCode);
        this.click(Selector.REPORTING_FACILITY_QUICK_CODE_LOOKUP_BUTTON);
    }

    setOrderingFacility(orderingFacility: Organization): void {
        this.setText(Selector.ORDERING_FACILITY_QUICK_CODE, orderingFacility.quickCode);
        this.click(Selector.ORDERING_FACILITY_QUICK_CODE_LOOKUP_BUTTON);
    }

    setOrderingProvider(orderingProvider: Organization): void {
        this.setText(Selector.ORDERING_PROVIDER_QUICK_CODE, orderingProvider.quickCode);
        this.click(Selector.ORDERING_PROVIDER_QUICK_CODE_LOOKUP_BUTTON);
    }

    setProgramArea(programArea: ProgramArea): void {
        this.setText(Selector.PROGRAM_AREA, programArea);
    }

    setJurisdiction(jurisdiction: string): void {
        this.setText(Selector.JURISDICTION, jurisdiction);
    }

    setOrderedTest(orderedTest: Test): void {
        this.setText(Selector.ORDERED_TEST, orderedTest.description);
    }

    setAccessionNumber(accessionNumber: string): void {
        this.setText(Selector.ACCESSION_NUMBER, accessionNumber);
    }

    setSpecimenSource(specimenSource: SpecimenSource): void {
        this.setText(Selector.SPECIMEN_SOURCE, specimenSource);
    }

    setSpecimentCollectionSite(collectionSite: CollectionSite): void {
        this.setText(Selector.SPECIMEN_SITE, collectionSite);
    }

    setSpecimentCollectionDate(date: Date): void {
        let dateString = DateUtil.getNBSFormattedDate(date, false);
        this.setText(Selector.SPECIMEN_COLLECTION_DATE, dateString);
    }

    setPatientStatusAtCollection(status: PatientStatus): void {
        this.setText(Selector.PATIENT_STATUS_AT_COLLECTION, status);
    }

    setResultedTest(test: Test): void {
        this.setText(Selector.RESULTED_TEST, test.description);
    }

    setCodedResult(result: CodedResult): void {
        this.setText(Selector.CODED_RESULT, result.description);
    }

    setNumericResult(numericResult: string): void {
        this.setText(Selector.NUMERIC_RESULTS, numericResult);
    }

    setTextResults(textResults: string): void {
        this.setText(Selector.TEXT_RESULTS, textResults);
    }

    setReferenceRangeFrom(referenceRangeFrom: string): void {
        this.setText(Selector.REFERENCE_RANGE_FROM, referenceRangeFrom);
    }

    setReferenceRangeTo(referenceRangeTo: string): void {
        this.setText(Selector.REFERENCE_RANGE_TO, referenceRangeTo);
    }

    setResultedTestStatus(status: string): void {
        this.setText(Selector.RESULT_STATUS, status);
    }

    setResultsComment(comments: string): void {
        this.setText(Selector.RESULT_COMMENTS, comments);
    }

    clickAddResultedTest(): void {
        this.click(Selector.ADD_RESULTED_TEST_BUTTON);
    }

    setLabReportComments(comments: string): void {
        this.setText(Selector.LAB_REPORT_COMMENTS, comments);
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }

    public navigateTo(): void {
        cy.visit(this.relativeUrl, this.defaultOptions);
        // The page takes a moment before the tabs are ready
        cy.wait(100, this.defaultOptions);
    }
}
