import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicSearchPatientPage from "cypress/e2e/pages/nbs-classic/patient.page";

Then("Navigate to classic Patient Search pane", () => {
    classicSearchPatientPage.navigateToClassicPatientSearchPane()
});

Then("Enter Last Name in input text field {string}", (text) => {
    classicSearchPatientPage.enterLastNameInClassicSearchPatientPage(text)
});

Then("Click on Search in classic Patient Search pane", () => {
    classicSearchPatientPage.clickSearchBtnInClassicPatientSearchPane()
});

Then("Select a patient to edit through classic search", () => {
    classicSearchPatientPage.selectPatientToEdit()
});

Then("Edit patient details showing on the page", () => {
    classicSearchPatientPage.viewPatientDetails()
});
