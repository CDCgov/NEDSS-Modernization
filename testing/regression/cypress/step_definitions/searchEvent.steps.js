import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchEventPage } from "cypress/e2e/pages/searchEvent.page";
import  AddLabReport  from "cypress/e2e/pages/events/add-lab-report.page";
// import patientProfilePage from "cypress/e2e/pages/patientProfile.page";

Then("I navigate the event investigation", () => {
  searchEventPage.clickEventInvestigation();
});

Then("I navigate the event laboratory report", () => {
  searchEventPage.clickEventLabReport();
});

Then("I click criteria tab", () => {
  searchEventPage.clickEventInvestigationCriteria();
});

Then("I select a condition for event investigation", () => {
  searchEventPage.selectEventInvestigationCondition();
  searchEventPage.search();
});

Then("I select program area for event laboratory report", () => {
  searchEventPage.selectEventLabReportProgramArea();
  searchEventPage.search();
});

Then("I should see Results with the link {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});

Then("I should see Results with the text {string}", (string) => {
  cy.get(".text-normal").contains(string).should("be.visible");
});

Then("I select a program area for event investigation", () => {
  searchEventPage.selectEventInvestigationProgramArea();
  searchEventPage.search();
});

Then("I select a jurisdiction for event investigation", () => {
  searchEventPage.selectEventInvestigationJurisdiction();
  searchEventPage.search();
});

Then("I select a jurisdiction for event laboratory report", () => {
  searchEventPage.selectEventLabReportJurisdiction();
  searchEventPage.search();
});

Then("I select a pregnancy for event investigation", () => {
  searchEventPage.selectPregnancy();
  searchEventPage.search();
});

Then("I select a pregnancy for event laboratory report", () => {
  searchEventPage.selectPregnancy();
  searchEventPage.search();
});

Then("I select a user edited by for event investigation", () => {
  searchEventPage.selectInvestigationUpdatedBy();
  searchEventPage.search();
});

Then("I select a user created by for event investigation", () => {
  searchEventPage.selectInvestigationCreatedBy();
  searchEventPage.search();
});

Then("I select a date event range for event investigation", () => {
  searchEventPage.selectInvestigationEventDate();
  searchEventPage.search();
});

Then("I select a event id type for event investigation", () => {
  searchEventPage.selectInvestigationEventType();
  searchEventPage.search();
});

Then("I select a event id type for event laboratory report", () => {
  searchEventPage.selectLabReportEventType();
  searchEventPage.search();
});

Then("I select a facility for event investigation", () => {
  searchEventPage.selectInvestigationFacility();
  searchEventPage.search();
});

Then("I select a facility for event laboratory report", () => {
  searchEventPage.selectLabReportFacility();
  searchEventPage.search();
});

Then("I select a provider for event laboratory report", () => {
  searchEventPage.selectLabReportProvider();
  searchEventPage.search();
});

Then("I select a provider for event investigation", () => {
  searchEventPage.selectInvestigationProvider();
  searchEventPage.search();
});

Then("I select a investigation status for event investigation", () => {
  searchEventPage.selectInvestigationStatus();
  searchEventPage.search();
});

Then("I select outbreak name for event investigation", () => {
  searchEventPage.selectInvestigationOutbreakName();
  searchEventPage.search();
});

Then("I select case study for event investigation", () => {
  searchEventPage.selectInvestigationCaseStudy();
  searchEventPage.search();
});

Then("I select investigation current processing status for event investigation", () => {
  searchEventPage.selectInvestigationCurrentProcessingStatus();
  searchEventPage.search();
});

Then("I select notification status status for event investigation", () => {
  searchEventPage.selectInvestigationNotificationStatus();
  searchEventPage.search();
});

When("user creates lab report and investigation for patient id {string}", (string) => {
  cy.contains("button", "Add lab report").scrollIntoView().click();
  // patientProfilePage.clickOnButton("Add lab report");
  cy.contains("button", "Add lab report").scrollIntoView().click();
  AddLabReport.addLabReport();
});