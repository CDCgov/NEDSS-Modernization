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

Then("I am on the New patient Extended form", () => {
    classicSearchPatientPage.goToNewPatientExtendedForm()
});

Then("I have filled out all required fields in all sections", () => {
    classicSearchPatientPage.fillExtendedFormDetails()
});

Then("I click the Save button", () => {
    classicSearchPatientPage.clickSaveExtendedForm()
});

Then("Form should be submitted successfully without errors", () => {
    classicSearchPatientPage.VerifySuccessfulFormSubmit()
});

Then("I should receive a confirmation message", () => {
    classicSearchPatientPage.verifyConfirmationMessage()
});

Then("I have filled out all Information as of Date field", () => {
    classicSearchPatientPage.fillInformationAsOfDateField()
});
