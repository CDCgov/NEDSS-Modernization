import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchEventPage } from "cypress/e2e/pages/searchEvent.page";

Then("I navigate the event investigation", () => {
  searchEventPage.clickEventInvestigation();
});

Then("I select a condition for event investigation", () => {
  searchEventPage.selectEventInvestigationCondition();
  searchEventPage.eventSearch();
});