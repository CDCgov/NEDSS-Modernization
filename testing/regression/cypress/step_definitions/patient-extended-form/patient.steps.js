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

Then("I click Add Identification Button", () => {
    classicSearchPatientPage.clickAddIdentificationButton();
 });

 Then("I click Add Phone and Email Button", () => {
    classicSearchPatientPage.clickAddPhoneButton();
 });

Then("I add type and use for phone", () => {
    classicSearchPatientPage.selectPhoneType();
    classicSearchPatientPage.selectPhoneUse();
    classicSearchPatientPage.typeValidPhoneNumber();
 });

Then("Select section {string} with id {string} option {string}", (text, text1, text2) => {
    classicSearchPatientPage.selectSectionField(text, text1, text2);
});

Then("Type section {string} with id {string} with text {string}", (text, text1, text2) => {
    classicSearchPatientPage.typeInputSectionField(text, text1, text2);
});

Then("Error section {string} with error {string}", (text, text1) => {
    classicSearchPatientPage.errorSectionField(text, text1);
});

Then("I have successfully added a new patient", () => {
    classicSearchPatientPage.addNewPatient();
});

Then("Add Patient Success modal is displayed", () => {
    classicSearchPatientPage.successModalDisplayed();
});

Then("I click the Add lab report button", () => {
    classicSearchPatientPage.clickAddLabReportBtn();
});

Then("I should be redirected to the Add Lab Report form", () => {
    classicSearchPatientPage.redirectAddLabReportForm();
});

Then("I enter a valid Reporting Facility", () => {
    classicSearchPatientPage.enterReportingFacility();
});

Then("I select a valid Program Area", () => {
    classicSearchPatientPage.selectProgramArea();
});

Then("I select a valid Jurisdiction", () => {
    classicSearchPatientPage.selectJurisdiction();
});

Then("I select a valid Resulted Test and fill in the details", () => {
    classicSearchPatientPage.selectResultedTestAndFillDetails();
});

Then("I click the Submit button in Report form", () => {
    classicSearchPatientPage.clickAddReportFormSubmitBtn();
});

Then("I should see the patients profile displayed with the added lab report", () => {
    classicSearchPatientPage.verifyPatientProfileWithAddedLabReport();
});

Then("I click the Add investigation button", () => {
    classicSearchPatientPage.clickAddInvestigationBtn();
});

Then("I should be redirected to the Add Investigation form", () => {
    classicSearchPatientPage.redirectAddInvestigationForm();
});

Then("I select a valid Condition", () => {
    classicSearchPatientPage.selectCondition();
});

Then("I select a valid Jurisdiction in investigation form", () => {
    classicSearchPatientPage.selectJurisdictionInInvestigationForm();
});

Then("I click the Submit button in Add Investigation Form", () => {
    classicSearchPatientPage.clickAddInvestigationFormSubmitBtn();
});

Then("I should see a success message indicating that the investigation has been added successfully", () => {
    classicSearchPatientPage.verifyInvestigationAddedSuccessfully();
});

When("I select yes to Is the patient deceased", () => {
    NameEntryPage.selectPatientDeceasedYes();
  });

  When("I complete the Mortality fields", () => {
    NameEntryPage.completeMortalityFields();
  });

Then("I am on the modernized Patient Search page", () => {
    classicSearchPatientPage.navigateToModernizedPatientSearchPane();
});

Then("I select Starts with for Last Name", () => {
    classicSearchPatientPage.startsWithForLastName();
});

Then("I enter a partial Last Name {string}", (name) => {
    classicSearchPatientPage.enterLastNameInModernizedSearchPatientPage(name);
});

Then("Click on Search in modernized Patient Search pane", () => {
    classicSearchPatientPage.clickSearchBtnInModernizedPatientSearchPane()
});

Then("the system should return patients whose Last Name starts with the entered value", () => {
    classicSearchPatientPage.patientListEnteredValue();
});

Then("I select Contains for Last Name", () => {
    classicSearchPatientPage.containsForLastName();
});

Then("the system should return patients whose Last Name contains the entered value", () => {
    classicSearchPatientPage.patientListEnteredValue();
});

When("I select input id {string} with type {string}", (id, type) => {
    classicSearchPatientPage.selectSearchNameType(id, type);
});

Then("I fill input id {string} with text {string}", (id, text) => {
    classicSearchPatientPage.fillIdInputWithText(id, text);
});

Then("Verify text {string} in Search Result data type {string}", (text, type) => {
    classicSearchPatientPage.findSearchResultByDataItemType(text, type)
});

Then("I select Exact Date for Date of Birth in extended form", () => {
    classicSearchPatientPage.selectExactDateForDateOfBirth();
});

Then("I enter a specific date of birth in extended form", () => {
    classicSearchPatientPage.enterDateOfBirthInModernizedSearchPatientPage();
});

Then("the system should return patients whose Last Name Exact Date the entered value", () => {
    classicSearchPatientPage.patientListEnteredValueForDateOfBirth();
});

Then("I select Equal for Last Name", () => {
    classicSearchPatientPage.equalForLastName();
});

Then("I select Not Equal for Last Name", () => {
    classicSearchPatientPage.notEqualForLastName();
});

Then("the system should return patients whose Last Name Not Equal the entered value", () => {
    classicSearchPatientPage.patientListEnteredValue();
});

Then("I select Sounds like for Last Name", () => {
    classicSearchPatientPage.soundsLikeForLastName();
});

Then("the system should return patients whose Last Name Sounds like the entered value", () => {
    classicSearchPatientPage.patientListEnteredValue();
});

Then("the system should return patients whose Last Name Equal the entered value", () => {
    classicSearchPatientPage.patientListEnteredValue();
});
