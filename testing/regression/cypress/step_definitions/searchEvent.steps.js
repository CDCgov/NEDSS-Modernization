import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchEventPage } from "cypress/e2e/pages/searchEvent.page";

Then("I navigate the event investigation", () => {
  searchEventPage.clickEventInvestigation();
});

Then("I click criteria tab", () => {
  searchEventPage.expandInvestigationCriteria();
}); 

Then("I navigate the event laboratory report", () => {
  searchEventPage.clickEventLabReport();
});

Then("I select a condition for event investigation", () => {
  searchEventPage.selectEventInvestigationCondition();
  searchEventPage.search();
});

Then("I select program area for event laboratory report", () => {
  searchEventPage.selectEventLabReportProgramArea();
  searchEventPage.search();
});

Then("I should see Condition Results with the link {string}", (string) => {
  cy.get("a#condition").contains(string).scrollIntoView().should("be.visible");
});

Then("I should see Results with the link {string}", (string) => {
  cy.get("a#documentType").contains(string).scrollIntoView().should("be.visible");
});

Then("I should see Results with the text {string}", (string) => {
  cy.get("div[class^=result-item_item]").contains(string).should("be.visible");
});

Then("I check the ELR in search", () => {
  searchEventPage.checkELR();
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

Then("I select a date event range for laboratory report", () => {
  searchEventPage.selectLabReportEventDate();
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

Then("I select a entry method event laboratory report", () => {
  searchEventPage.selectLabReportEntryMethod();
  searchEventPage.search();
});

Then("I select a entered by event laboratory report", () => {
  searchEventPage.selectLabReportEnteredByMethod();
  searchEventPage.search();
});

Then("I select a event status event laboratory report", () => {
  searchEventPage.selectLabReportEventStatus();
  searchEventPage.search();
});

Then("I select a process status event laboratory report", () => {
  searchEventPage.selectLabReportProcessStatus();
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

Then("I select an investigation status for event investigation", () => {
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

Then("I select notification status for event investigation", () => {
  searchEventPage.selectInvestigationNotificationStatus();
  searchEventPage.search();
});

Then("I select resulted test for event laboratory report", () => {
  searchEventPage.selectLabReportResultTest();
  searchEventPage.expandLabReportCriteria();
  cy.get('input[id="resultedTest"]').type("LEAD");
  cy.get('.usa-combo-box__list-option').eq(1).click();
  searchEventPage.search();
});

Then("I unselect all the lab Entry method", () => {
  searchEventPage.selectLabReportCodedResult();
});

Then("I select coded result for event laboratory report", () => {
  searchEventPage.selectLabReportCodedResult();
  searchEventPage.expandLabReportCriteria();
  cy.get('input[id="codedResult"]').type("abnormal");
  cy.get('.usa-combo-box__list-option').eq(0).click();
  searchEventPage.search();
});
