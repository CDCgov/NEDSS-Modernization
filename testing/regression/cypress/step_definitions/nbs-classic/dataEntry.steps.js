import { labReportPage } from '../../e2e/pages/nbs-classic/dataEntry.page';
import { patientEntitySearch } from '../../e2e/pages/nbs-classic/patientEntitySearch.page';
import { searchPage } from '../../e2e/pages/search.page';
import { morbidityReportPage } from '../../e2e/pages/nbs-classic/morbidityReportPage';
import addLabReportInvestigationPage from '../../e2e/pages/events/add-lab-report-investigation.page';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { clickSubmitButton, clickHome } from '../../e2e/pages/nbs-classic/utils';

When("I search for patient {string} {string}", (patientFirstName, patientLastName) => {
  patientEntitySearch.getPatientByName({patientLastName, patientFirstName});
});

When("I click on patient ID {string} to view profile", (patientId) => {
  searchPage.clickPatientId(patientId);
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
  labReportPage.clickAddButtonResultedTests();
});

When("I click the submit button", () => {
  clickSubmitButton();
});

When("I click the Cancel button on the Morbidity Report page", () => {
  morbidityReportPage.clickCancel();
});

When("I click the Submit and Create Investigation button", () => {
  morbidityReportPage.clickSubmitAndCreateInvestigation();
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

When("I select {string} from the Pregnant dropdown menu", (value) => {
  morbidityReportPage.selectPregnant(value);
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

When("I click the Edit button on the Morbidity Report page", () => {
  morbidityReportPage.clickEdit();
});

Then("clicking the Print button should display the print preview", () => {
  morbidityReportPage.clickAndVerifyPrint();
});

When("I enter the current date in the Treatment Date field", () => {
  const today = new Date().toLocaleDateString("en-US");
  morbidityReportPage.enterTreatmentDate(today);
});

When("I select {string} from the Treatment dropdown menu", (treatment) => {
  morbidityReportPage.selectTreatment(treatment);
});

When("I click the Add Treatment button", () => {
  morbidityReportPage.clickAddTreatment();
});

When("I click the Transfer Ownership button on the Morbidity Report page", () => {
  morbidityReportPage.clickTransferOwnership();
});

When("I click the Create Investigation button on the Morbidity Report page", () => {
  morbidityReportPage.clickCreateInvestigation();
});

When("I click the Associate Investigation button on the Morbidity Report page", () => {
  morbidityReportPage.clickAssociateInvestigation();
});

When("I store the morbidity report's condition", () => {
  morbidityReportPage.storeCondition();
});

When("I check an investigation with the condition {string}", (condition) => {
  morbidityReportPage.checkFirstInvestigationWithCondition(condition);
});

When("I store the Investigation ID from the association message", () => {
  morbidityReportPage.storeInvestigationIdFromAssociationMessage();
});

When("I click the Mark as Reviewed button on the Morbidity Report page", () => {
  morbidityReportPage.clickMarkAsReviewed();
});

When("I click the Mark as Reviewed button on the Morbidity Report page and handle the popup", () => {
  morbidityReportPage.clickMarkAsReviewedAndHandlePopup();
});

When("I click the delete button on the Morbidity Report page", () => {
  morbidityReportPage.clickDelete();
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

// Row 118: Add new lab report and create investigation

When("user selects {string} from the Program Area dropdown", (text) => {
  labReportPage.selectProgramAreaByText(text);
});

When("user selects {string} from the Jurisdiction dropdown", (text) => {
  labReportPage.selectJurisdictionByText(text);
});

When("user selects {string} from the Resulted Test dropdown", (text) => {
  labReportPage.selectResultedTestByText(text);
});

When("user selects {string} from the Coded Result dropdown", (text) => {
  labReportPage.selectCodedResultByText(text);
});

When("user clicks the Submit and Create Investigation button", () => {
  labReportPage.clickSubmitAndCreateInvestigation();
});

When("user clicks the Cancel button on the Lab Report page and confirms", () => {
  labReportPage.clickCancelAndConfirm();
});

Then("the Lab Report view should show Reporting Facility {string}", (text) => {
  labReportPage.verifyViewedReportingFacility(text);
});

Then("the Lab Report view should show Program Area {string}", (text) => {
  labReportPage.verifyViewedProgramArea(text);
});

Then("the Lab Report view should show Jurisdiction {string}", (text) => {
  labReportPage.verifyViewedJurisdiction(text);
});

Then("the Lab Report view should show Resulted Test {string}", (text) => {
  labReportPage.verifyViewedResultedTest(text);
});

When("user selects {string} as the condition for the new investigation", (conditionText) => {
  addLabReportInvestigationPage.selectCondition(conditionText);
});

When(
  "user sets the processing decision to {string} for a {string} investigation and submits",
  (decisionCode, investigationType) => {
    addLabReportInvestigationPage.selectProcessingDecisionAndSubmit(decisionCode, investigationType);
  }
);

When("user fills the Field Follow-up investigator with Quick Code {string}", (quickCode) => {
  addLabReportInvestigationPage.fillFieldFollowUpInvestigator(quickCode);
});

When("user sets the Field Follow-up date assigned to match the investigation start date", () => {
  addLabReportInvestigationPage.fillFieldFollowUpDateAssignedToMatchStartDate();
});

When("user selects {string} for Patient Eligible for Notification of Exposure", (text) => {
  addLabReportInvestigationPage.selectNotificationEligibility(text);
});

When("user clicks the Submit button on the investigation", () => {
  addLabReportInvestigationPage.clickSubmit();
});

Then("the investigation should be saved successfully", () => {
  addLabReportInvestigationPage.verifyInvestigationSavedSuccessfully();
});
