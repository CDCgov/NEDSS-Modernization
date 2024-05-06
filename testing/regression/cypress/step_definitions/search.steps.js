import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchPage } from "cypress/e2e/pages/search.page";

Then("I navigate the  basic info and address", () => {
  searchPage.selectBasicInfo();
  searchPage.selectAddress(); 
});

Then("I navigate the event investigation", () => {
  searchPage.clickEventInvestigation();
});

Then("I select a condition for event investigation", () => {
  searchPage.selectEventInvestigationCondition();
  searchPage.eventSearch();
});

When("I search for {string} city", (string) => {
  searchPage.enterCity(string);
  searchPage.search();
});

When("I enter {string} as zip code", (string) => {
  searchPage.enterZipCode(string);
});

When("I search for {string} zip code", (string) => {
  searchPage.enterZipCode(string);
  searchPage.search();
});

When("I search for {string} Street address", (string) => {
  searchPage.enterStreetAddress(string);
  searchPage.search();
});

Then("I select {string} state", (string) => {
  searchPage.selectState(string);
  searchPage.search();
});

When("I search for Street Address {string} City {string} State {string} Zip code {string}", function (string, string2, string3, string4) {
  searchPage.enterStreetAddress(string);
  searchPage.enterCity(string2);
  searchPage.selectState(string3);
  searchPage.enterZipCode(string4);
  searchPage.search();
});

Then("I should see No Results found text", () => {
  cy.get("div.advanced-search-message p")
    .invoke("text")
    .should("match", /^No results found/);
});

Then("I should found result patient profile", () => {
  cy.get("#resultsCount .advanced-search-results-title")
    .invoke("text")
    // .should("match", /^No results found/);
});

When("I search by last name as {string}", (string) => {
  searchPage.enterLastName(string);
  searchPage.search();
});

When("I search by patient id as {string}", (string) => {
  searchPage.enterPatiendID(string);
  searchPage.search();
});

When("I search by first name as {string}", (string) => {
  searchPage.enterFirstName(string);
  searchPage.search();
});

When("I search by dob as {string}", (string) => {
  searchPage.enterDob(string);
  searchPage.search();
});

When("I search by sex as {string}", (string) => {
  searchPage.selectGender(string);
  searchPage.search();
});

Then("I should see Results with the last name {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});

Then("I should see Results with the first name {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});

Then("I should see Results with the sex {string}", (string) => {
  cy.get("p").contains(string).should("be.visible");
});

Then("I should see Results with the patient id {string}", (string) => {
  cy.get("p").contains(string).should("be.visible");
});

Then("I navigate to contact section", () => {
  searchPage.selectBasicInfo();
  searchPage.selectContact();
});

When("I search by phone number as {string}", (string) => {
  searchPage.enterPhone(string);
  searchPage.search();
});

When("I search by email as {string}", (string) => {
  searchPage.enterEmail(string);
  searchPage.search();
});

Then("I should see Results with the phone number as {string}", (string) => {
  cy.get("p").contains(string).should("be.visible");
});

Then("I should see Results with the email as {string}", (string) => {
  cy.get("p").contains(string).should("be.visible");
});

When("I navigate to id section", () => {
  searchPage.selectBasicInfo();
  searchPage.selectId();
});

Then(
  "I search by id type as {string} and id as {string}",
  (string, string2) => {
    searchPage.enterIdType(string);
    searchPage.enterId(string2);
    searchPage.search();
  }
);

Then(
  "I should see Results with the {string} as {string}",
  (string, string1) => {
    cy.get("p").contains(string1).should("be.visible");
  }
);

When("I navigate to race section", () => {
  searchPage.selectBasicInfo();
  searchPage.selectRace();
});

Then(
  "I search by ethnicity as {string} and race {string}",
  (string, string2) => {
    searchPage.enterEthnicity(string);
    searchPage.enterRace(string2);
    searchPage.search();
  }
);

Then(
  "I search by ethnicity as {string} and race not select",
  (string) => {
    searchPage.enterEthnicity(string);
    searchPage.search();
  }
);

Then(
  "I search by ethnicity not select and race {string}",
  (string) => {
    searchPage.enterRace(string);
    searchPage.search();
  }
);
