import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicSearchPatientPage from "cypress/e2e/pages/patient-extended-form/patient.page";
import NameEntryPage from "cypress/e2e/pages/patient-extended-form/patient-extended.page";


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

Then("I have filled out future date in Information as of Date field", () => {
    classicSearchPatientPage.fillInformationAsOfDateField("01/23/2055")
});

Then("Error message should appear right above Information as of Date field", () => {
    classicSearchPatientPage.errorMessageInformationAsOfField()
});

Then("I have filled out invalid text in Comments field", () => {
    classicSearchPatientPage.fillCommentsField('invalid')
});

Then("Error message should appear right above Comments field", () => {
    classicSearchPatientPage.errorMessageCommentsField()
});

Then("I have filled out empty text in Comments field", () => {
    classicSearchPatientPage.fillCommentsField("empty")
});

Then("I have filled out text in Comments field up to 2000 characters", () => {
    classicSearchPatientPage.fillCommentsField("2000")
});

Then("I clear Comments sections field", () => {
    classicSearchPatientPage.clearCommentsField()
});

When("I enter a valid name in the First and Last name fields", () => {
    NameEntryPage.enterValidFirstAndLastName();
  });

Then("the system should display an error message indicating that the field is required", () => {
    NameEntryPage.verifyRequiredFieldError();
  });

When("I leave the Type field empty", () => {
  });

Then("I have filled out Address input fields", () => {
    classicSearchPatientPage.fillExtendedAddressFormDetails()
});

Then("I have not filled out all Address input fields", () => {
    classicSearchPatientPage.fillExtendedAddressFormDetails('invalid')
});

Then("Error message should appear right above Address fields", () => {
    classicSearchPatientPage.errorMessageAddressField()
});

Then("I have filled out Dropdowns fields", () => {
    classicSearchPatientPage.fillDropdownFields()
});

When("I enter a valid phone number in the Phone field", () => {
    NameEntryPage.enterValidPhoneNumber();
  });

Then("I have not filled out Dropdowns fields", () => {
    classicSearchPatientPage.doNotFillDropdownValues()
});

Then("Error message should appear right above dropdown fields", () => {
    classicSearchPatientPage.errorMessageDropdownField()
});
