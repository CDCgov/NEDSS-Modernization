import { labReportPage } from '../../e2e/pages/nbs-classic/dataEntry.page';
import { patientEntitySearch } from '../../e2e/pages/nbs-classic/patientEntitySearch.page';
import { morbidityReportPage } from '../../e2e/pages/nbs-classic/morbidityReportPage';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { clickSubmitButton, clickHome } from '../../e2e/pages/nbs-classic/utils';

When("I search for patient {string} {string}", (patientFirstName, patientLastName) => {
  patientEntitySearch.getPatientByName({patientLastName, patientFirstName});
});

When("I check the Lab Report count", () => {
  labReportPage.getLabReportCountForPatient();
});

When('I click on Home in the navigation bar', () => {
  labReportPage.clickHome();
});

When('I click on Data Entry in the navigation bar', () => {
  labReportPage.clickDataEntry();
});

When("I click on Lab Report", () => {
  labReportPage.clickLabReport();
});

When("I click on the Lab Report tab", () => {
  labReportPage.clickLabReportTab();
});

When("I enter {string} in the Reporting Facility field", (value) => {
  labReportPage.enterReportingFacility(value);
});

When("I click the Quick Code Lookup button", () => {
  labReportPage.clickQuickCodeLookup();
});

When('I check the "Same as Reporting Facility" checkbox', () => {
  labReportPage.checkSameAsReportingFacility();
});

When("I select the third element in the Jurisdiction dropdown", () => {
  labReportPage.selectJurisdiction();
});

When("I select the sixth element in the Resulted Test dropdown", () => {
  labReportPage.selectResultedTest();
});

When("I select the second element in the Coded Result dropdown", () => {
  labReportPage.selectCodedResult();
});

When("I enter {string} in the Numeric Result field", (value) => {
  labReportPage.enterNumericResult(value);
});

When("I select % for the Units field", () => {
  labReportPage.selectUnits();
});

When("I enter {string} in the Text Result field", (text) => {
  labReportPage.enterTextResult(text);
});

When("I click the add button to add the lab report", () => {
  labReportPage.clickAddButton();
});

When("I click the submit button", () => {
  clickSubmitButton();
});

When("I click on the Morbidity Report link", () => {
  morbidityReportPage.clickMorbidityReport();
});

When("I click on the Patient tab", () => {
  morbidityReportPage.clickPatientTab();
});

When("I click on the Report Information tab", () => {
  morbidityReportPage.clickReportInformationTab();
});

When("I select {string} from the Condition dropdown menu", (condition) => {
  morbidityReportPage.selectCondition(condition);
});

When(
  "I select {string} from the Jurisdiction dropdown menu",
  (jurisdiction) => {
    morbidityReportPage.selectJurisdiction(jurisdiction);
  }
);

When("I clear the Jurisdiction field", () => {
  morbidityReportPage.clearJurisdiction();
});

When("I enter the current date in the Date of Morbidity Report field", () => {
  const today = new Date().toLocaleDateString("en-US");
  morbidityReportPage.enterMorbidityDate(today);
});

When(
  "I enter {string} in the Facility and Provider Information field",
  (value) => {
    morbidityReportPage.enterFacilityProvider(value);
  }
);

When("I click on the Code Lookup button", () => {
  morbidityReportPage.clickCodeLookup();
});

When("I click the Submit button", () => {
  morbidityReportPage.clickSubmit();
});

When('I confirm the submission by clicking "Ok"', () => {
  morbidityReportPage.confirmSubmission();
});

//Data Entry - Lab Report - Patient Tab

//  1: Patient Search

When("I populate the page with patient Surma J Singh's information", () => {
  labReportPage.searchForPatientInPopup();
});

When('I click Next to navigate to the Lab Report tab', () => {
  labReportPage.clickNext();
});

//Data Entry - Lab Report - Lab Report Tab

//  1: Facility and Provider Information


When("I search for Reporting Facility with Quick Code {string}", (quickCode) => {
  labReportPage.searchForReportingFacility(quickCode);
});

//  2: Order Details

When("I select a random Program Area", () => {
  labReportPage.selectProgramArea();
});



When("I select a random Jursidiction", () => {
  labReportPage.selectJurisdiction();
});

//  3: Ordered Test

When("I populate Ordered Test with Measles virus Rubeola antigen", () => {
  labReportPage.searchForOrderedTestInPopup();
});

When("I select a random Coded Result", () => {
  labReportPage.selectCodedResult();
});

When("I select a random Specimen Source", () => {
  labReportPage.selectSpecimenSource();
});

When("I select a random Specimen Site", () => {
  labReportPage.selectSpecimenSite();
});

When('I select {string} from the Specimen Source dropdown', (specimenSource) => {
  labReportPage.selectLabReportSpecimenSource(specimenSource);
});

When('I select {string} from the Specimen Site dropdown', (specimenSite) => {
  labReportPage.selectLabReportSpecimenSite(specimenSite);
});

When('I enter the current date in the Specimen Collection Date/Time field', () => {
  labReportPage.enterSpecimenCollectionDate();
});

//  4: Resulted Test

When('I select a random Resulted Test', () => {
  labReportPage.selectResultedTest();
});

When('I select {string} from the Code Result dropdown', (specimenSource) => {
  labReportPage.selectLabReportSpecimenSource(specimenSource);
});

When("I click the Add button under Resulted Tests", () => {
  labReportPage.clickAddButtonResultedTests();
});

//  5: Morbidity Report

When("I enter patient first name {string} and last name {string}", (firstName, lastName) => {
  morbidityReportPage.enterPatientBothNames(firstName, lastName);
});

// 6: Verification Steps

When("I go to the Home page", () => {
  clickHome();
});

Then("there should be one more Lab Report than before", () => {
  labReportPage.verifyLabReportCountIncreased();
});

Then("the last Lab Report should have multiple resulted tests associated with it", () => {
  labReportPage.verifyLastLabReportHasMultipleResultedTests();
});

Then("the morbidity report should be submitted successfully", () => {
  morbidityReportPage.verifySuccessfulSubmission();
});

// Validation error verification steps
Then("I should see validation errors for required fields", () => {
  morbidityReportPage.verifyValidationErrors();
});

Then("I should see a validation error for the Condition field", () => {
  morbidityReportPage.verifyFieldValidationError("Condition");
});

Then("I should see a validation error for the Jurisdiction field", () => {
  morbidityReportPage.verifyFieldValidationError("Jurisdiction");
});

// Form state verification steps
Then("the patient first name field should contain {string}", (value) => {
  morbidityReportPage.verifyPatientFirstNameContains(value);
});
