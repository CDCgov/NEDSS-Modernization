import { MaritalStatus } from '../models/enums/MaritalStatus';
import { Identification } from '../models/Patient';
import { Race } from '../models/enums/Race';
import { Suffix } from '../models/enums/Suffix';
import BasePage from './BasePage';
import DateUtil from '../utils/DateUtil';
import { State } from '../models/enums/State';

enum Selector {
    AS_OF_DATE = 'input[name=patientAsOfDateGeneral]',
    COMMENTS = 'textarea[name=person\\.thePersonDT\\.description]',
    LAST_NAME = 'input[name=person\\.thePersonDT\\.lastNm]',
    FIRST_NAME = 'input[name=person\\.thePersonDT\\.firstNm]',
    MIDDLE_NAME = 'input[name=person\\.thePersonDT\\.middleNm]',
    SUFFIX_INPUT = 'input[name=DEM107_textbox]',
    DOB = 'input[name=patientBirthTime]',
    CURRENT_SEX = 'input[name=DEM113_textbox]',
    BIRTH_SEX = 'input[name=DEM114_textbox]',
    DECEASED = 'input[name=DEM127_textbox]',
    MARITAL_STATUS = 'input[name=DEM140_textbox]',
    STREET_ADDRESS_1 = 'input[name=address\\[0\\]\\.thePostalLocatorDT_s\\.streetAddr1]',
    STREET_ADDRESS_2 = 'input[name=address\\[0\\]\\.thePostalLocatorDT_s\\.streetAddr2]',
    CITY = 'input[name=address\\[0\\]\\.thePostalLocatorDT_s\\.cityDescTxt]',
    STATE = 'input[name=DEM162_textbox]',
    ZIP = 'input[name=address\\[0\\]\\.thePostalLocatorDT_s\\.zipCd]',
    COUNTY = 'input[name=DEM165_textbox]',
    CENSUS_TRACT = 'input[name=address\\[0\\]\\.thePostalLocatorDT_s\\.censusTract]',
    COUNTRY = 'input[name=DEM167_textbox]',
    HOME_PHONE = 'input[name=patientHomePhone]',
    WORK_PHONE = 'input[name=patientWorkPhone]',
    WORK_PHONE_EXT = 'input[name=patientWorkPhoneExt]',
    CELL_PHONE = 'input[name=patientCellPhone]',
    EMAIL = 'input[name=patientEmail]',
    ETHNICITY = 'input[name=DEM155_textbox]',
    RACE_AMERICAN_INDIAN_OR_ALASKA_NATIVE = 'input[name=pamClientVO\\.americanIndianAlskanRace]',
    RACE_ASIAN = 'input[name=pamClientVO\\.asianRace]',
    RACE_BLACK_OR_AFRICAN_AMERICAN = 'input[name=pamClientVO\\.africanAmericanRace]',
    RACE_NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER = 'input[name=pamClientVO\\.hawaiianRace]',
    RACE_WHITE = 'input[name=pamClientVO\\.whiteRace]',
    RACE_OTHER = 'input[name=pamClientVO\\.otherRace]',
    RACE_REFUSED_TO_ANSWER = 'input[name=pamClientVO\\.refusedToAnswer]',
    RACE_NOT_ASKED = 'input[name=pamClientVO\\.notAsked]',
    RACE_UNKNOWN = 'input[name=pamClientVO\\.unKnownRace]',
    ID_TYPE_INPUT = 'input[name=typeID_textbox]',
    ID_ASSIGNING_AUTHORITY = 'input[name=assigningAuthority_textbox]',
    ID_VALUE = 'input[name=idValue]',
    ID_ADD = 'tr[id=AddButtonToggleIdSubSection] td input',
    SUBMIT_BUTTON = 'input[name=Submit]'
}
export default class AddPatientPage extends BasePage {
    constructor() {
        super('/PatientSearchResults1.do?ContextAction=Add');
    }

    public navigateTo(): void {
        throw new Error('NBS does not allow direct navigation to add patient page');
    }

    setComments(comments: string): void {
        this.setText(Selector.COMMENTS, comments);
    }

    setLastName(lastName: string): void {
        this.setText(Selector.LAST_NAME, lastName);
    }

    setFirstName(firstName: string): void {
        this.setText(Selector.FIRST_NAME, firstName);
    }

    setMiddleName(middleName: string): void {
        this.setText(Selector.MIDDLE_NAME, middleName);
    }

    setSuffix(suffix: Suffix): void {
        this.setText(Selector.SUFFIX_INPUT, suffix.toString());
    }

    setDob(dob: Date): void {
        this.setText(Selector.DOB, DateUtil.getNBSFormattedDate(dob, false));
    }

    setCurrentSex(currentSex: 'Female' | 'Male' | 'Unknown'): void {
        this.setText(Selector.CURRENT_SEX, currentSex);
    }

    setBirthSex(birthSex: 'Female' | 'Male' | 'Unknown'): void {
        this.setText(Selector.BIRTH_SEX, birthSex);
    }

    setDeceased(deceased: 'Yes' | 'No' | 'Unknown' | ''): void {
        this.setText(Selector.DECEASED, deceased);
    }

    setMaritalStatus(maritalStatus: MaritalStatus): void {
        this.setText(Selector.MARITAL_STATUS, maritalStatus.toString());
    }

    setStreetAddress1(streetAddr1: string): void {
        this.setText(Selector.STREET_ADDRESS_1, streetAddr1);
    }

    setStreetAddress2(streetAddr2: string): void {
        this.setText(Selector.STREET_ADDRESS_2, streetAddr2);
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

    setCensusTract(censusTract: string): void {
        this.setText(Selector.CENSUS_TRACT, censusTract);
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

    setEthnicity(ethnicity: 'Hispanic or Latino' | 'Not Hispanic or Latino' | ''): void {
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
    setRaces(races: Race[]): void {
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

    private clearIdentifications(): void {
        cy.document(this.defaultOptions).then((document) => {
            const idDeleteButtons = document.querySelectorAll('input[id^=deleteIdSubSection]');
            idDeleteButtons.forEach((btn) => {
                const inputElem = btn as HTMLInputElement;
                if (!inputElem.id.endsWith('Section')) {
                    // if it doesnt have a number at the end it is a hidden button that will trigger a form submit
                    inputElem.click();
                }
            });
        });
    }

    setIdentifications(identifications: Identification[]): void {
        this.clearIdentifications();
        identifications.forEach((id) => {
            this.setText(Selector.ID_TYPE_INPUT, id.identificationType.toString());

            this.setText(Selector.ID_ASSIGNING_AUTHORITY, id.assigningAuthority);
            this.setText(Selector.ID_VALUE, id.idNumber);
            this.click(Selector.ID_ADD);
        });
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }
}
