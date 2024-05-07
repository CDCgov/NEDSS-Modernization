import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchEventPage } from "cypress/e2e/pages/searchEvent.page";

Then("I navigate the event investigation", () => {
  searchEventPage.clickEventInvestigation();
});

Then("I select a condition for event investigation", () => {
  searchEventPage.selectEventInvestigationCondition();
  searchEventPage.search();
});

Then("I should see Results with the Condition {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});
