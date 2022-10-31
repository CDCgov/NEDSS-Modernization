/// <reference types="cypress" />

import { Then } from 'cypress-cucumber-preprocessor/steps';
import PatientSearchPage from '../pages/PatientSearchPage';
import PatientMother from '../utils/PatientMother';

Then(/I can view a patient's summary/, () => {
    const patient = PatientMother.patient();
    const resultsPage = PatientSearchPage.searchForPatient(patient);
    resultsPage.getPatientFilePage(0).then((patientFilePage) => {
        patientFilePage.navigateTo();
        patientFilePage.assertPatientSummaryExists(patient);
    });
});
