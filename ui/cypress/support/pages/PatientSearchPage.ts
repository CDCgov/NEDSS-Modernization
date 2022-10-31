import { AddressOperator, DobOperator, NameOperator } from '../models/enums/Operators';
import { PatientIdentificationType } from '../models/enums/PatientIdentificationType';
import { Race } from '../models/enums/Race';
import { Sex } from '../models/enums/Sex';
import { State } from '../models/enums/State';
import Patient from '../models/Patient';
import BasePage from './BasePage';
import PatientSearchResultsPage from './PatientSearchResultsPage';

enum Selector {
    LAST_NAME_OPERATOR = 'input[name=DEM102O_textbox]',
    LAST_NAME = 'input[name=personSearch\\.lastName]',
    FIRST_NAME_OPERATOR = 'input[name=DEM104O_textbox]',
    FIRST_NAME = 'input[name=personSearch\\.firstName]',
    DOB_OPERATOR = 'input[name=DEM105O_textbox]',
    DOB_EQUAL_MONTH = 'input[name=personSearch\\.birthTimeMonth]',
    DOB_EQUAL_DAY = 'input[name=personSearch\\.birthTimeDay]',
    DOB_EQUAL_YEAR = 'input[name=personSearch\\.birthTimeYear]',
    DOB_BETWEEN_FROM = 'input[name=personSearch\\.beforeBirthTime]',
    DOB_BETWEEN_TO = 'input[name=personSearch\\.afterBirthTime]',
    CURRENT_SEX = 'input[name=DEM114_textbox]',
    STREET_ADDRESS_OPERATOR = 'input[name=DEM106O_textbox]',
    STREET_ADDRESS = 'input[name=personSearch\\.streetAddr1]',
    CITY_OPERATOR = 'input[name=DEM107O_textbox]',
    CITY = 'input[name=personSearch\\.cityDescTxt]',
    STATE = 'input[name=DEM108O_textbox]',
    ZIP = 'input[name=personSearch\\.zipCd]',
    PATIENT_IDS = 'input[name=personSearch\\.localID]',
    ID_TYPE = 'input[name=DEM222_textbox]',
    ID_NUMBER = 'input[name=personSearch\\.rootExtensionTxt]',
    PHONE_NUMBER = 'input[name=personSearch\\.phoneNbrTxt]',
    EMAIL = 'input[name=personSearch\\.emailAddress]',
    ETHNICITY = 'input[name=DEM221_textbox]',
    RACE = 'input[name=DEM176_textbox]',
    ACTIVE_CHECKBOX = 'input[name=personSearch\\.active]',
    DELETED_CHECKBOX = 'input[name=personSearch\\.inActive]',
    SUPERCEDED_CHECKBOX = 'input[name=personSearch\\.superceded]',
    SUBMIT_BUTTON = 'input[name=Submit]'
}
export default class PatientSearchPage extends BasePage {
    constructor() {
        super('/MyTaskList1.do?ContextAction=GlobalPatient');
    }

    setLastNameOperator(operator: NameOperator): void {
        this.setText(Selector.LAST_NAME_OPERATOR, operator);
    }

    setLastName(lastName: string): void {
        this.setText(Selector.LAST_NAME, lastName);
    }

    setFirstNameOperator(operator: NameOperator): void {
        this.setText(Selector.FIRST_NAME_OPERATOR, operator);
    }

    setFirstName(firstName: string): void {
        this.setText(Selector.FIRST_NAME, firstName);
    }

    setDobOperator(operator: DobOperator): void {
        this.setText(Selector.DOB_OPERATOR, operator);
    }

    setDateOfBirth(dateOfBirth: Date): void {
        this.setText(Selector.DOB_EQUAL_DAY, dateOfBirth.getDate().toString());
        this.setText(Selector.DOB_EQUAL_MONTH, (dateOfBirth.getMonth() + 1).toString());
        this.setText(Selector.DOB_EQUAL_YEAR, dateOfBirth.getFullYear().toString());
    }

    setDateOfBirthBetween(fromDate: Date, toDate: Date): void {
        // NBS expects MM/dd/YYYY
        const fromString = `${fromDate.getMonth() + 1}/${fromDate.getDate()}/${fromDate.getFullYear()}`;
        const toString = `${toDate.getMonth() + 1}/${toDate.getDate()}/${toDate.getFullYear()}`;
        this.setText(Selector.DOB_BETWEEN_FROM, fromString);
        this.setText(Selector.DOB_BETWEEN_TO, toString);
    }

    setCurrentSex(sex: Sex): void {
        this.setText(Selector.CURRENT_SEX, sex);
    }

    setStreetAddressOperator(operator: AddressOperator): void {
        this.setText(Selector.STREET_ADDRESS_OPERATOR, operator);
    }

    setStreetAddress(streetAddress: string): void {
        this.setText(Selector.STREET_ADDRESS, streetAddress);
    }

    setCityOperator(operator: AddressOperator): void {
        this.setText(Selector.CITY_OPERATOR, operator);
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

    setPatientIds(patientIds: string[]): void {
        if (patientIds && patientIds.length > 0) {
            this.setText(Selector.PATIENT_IDS, patientIds.join(','));
        }
    }

    setIdType(idType: PatientIdentificationType): void {
        this.setText(Selector.ID_TYPE, idType.toString());
    }

    setIdNumber(idNumber: string): void {
        this.setText(Selector.ID_NUMBER, idNumber);
    }

    setPhoneNumber(phoneNumber: string): void {
        this.setText(Selector.PHONE_NUMBER, phoneNumber);
    }

    setEmail(email: string): void {
        this.setText(Selector.EMAIL, email);
    }

    setEthnicity(ethnicity: 'Hispanic or Latino' | 'Not Hispanic or Latino' | ''): void {
        this.setText(Selector.ETHNICITY, ethnicity);
    }

    setRace(race: Race): void {
        this.setText(Selector.RACE, race.toString());
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }

    static searchForPatient(patient: Patient): PatientSearchResultsPage {
        const searchPage = new PatientSearchPage();
        searchPage.navigateTo();
        searchPage.setLastName(patient.lastName);
        searchPage.setFirstName(patient.firstName);
        searchPage.setDateOfBirth(patient.dateOfBirth);
        searchPage.setCurrentSex(patient.currentSex);
        searchPage.setStreetAddress(patient.streetAddress);
        searchPage.setCity(patient.city);
        searchPage.setState(patient.state);
        searchPage.setZip(patient.zip);
        searchPage.setPatientIds(patient.patientIds);
        searchPage.setEthnicity(patient.ethnicitiy);
        searchPage.setRace(patient.races[0]);
        searchPage.clickSubmit();
        return new PatientSearchResultsPage();
    }
}
