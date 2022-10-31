import { Given, Then, When } from 'cypress-cucumber-preprocessor/steps';
import MergePatientSearchPage from '../pages/MergePatientSearchPage';
import PatientComparePage from '../pages/PatientComparePage';
import PatientMother from '../utils/PatientMother';
import PatientUtil from '../utils/PatientUtil';
import UserMother from '../utils/UserMother';
import UserUtil from '../utils/UserUtil';

let comparePage: Cypress.Chainable<PatientComparePage>;

Given(/A duplicated patient exists/, () => {
    UserUtil.login(UserMother.systemAdmin()).then(() => {
        UserUtil.createOrActivateUser(UserMother.supervisor());
    });
    UserUtil.login(UserMother.supervisor()).then(() => {
        PatientUtil.createPatientIfNotExists(PatientMother.patient());
        PatientUtil.createPatientIfNotExists(PatientMother.duplicatedPatient());
    });
});

When(/I compare merge candidates/, () => {
    const mergeSearchPage = new MergePatientSearchPage();
    mergeSearchPage.navigateTo();
    mergeSearchPage.setLastName(PatientMother.patient().lastName);
    mergeSearchPage.setFirstName(PatientMother.patient().firstName);
    const mergeSearchResultsPage = mergeSearchPage.clickSubmit();
    comparePage = mergeSearchResultsPage.compareFirstTwo();
});

Then(/I can view both patient summaries/, () => {
    comparePage.then((comparePatientPage) => {
        comparePatientPage.navigateTo();
        comparePatientPage.assertPatientDataIsDisplayed(PatientMother.patient(), PatientMother.duplicatedPatient());
    });
});
