import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { searchPage } from "cypress/e2e/pages/search.page";

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

Then("I should see no results found text", () => {
  cy.contains("No results found").should("be.visible");
});

Then("I should see No Results found text", () => {
  cy.contains("No results found").should("be.visible");
});

Then("I should see no result found text", () => {
  cy.contains("No result found").should("be.visible");
});


Then("I should found result patient profile", () => {
  cy.contains("No result found").should("be.visible");
});

When("I search by last name as {string}", (string) => {
  searchPage.enterLastName(string);
  searchPage.search();
});

When("I fill last name as {string}", (string) => {
  searchPage.enterLastName(string);
});

When("I search by patient id as {string}", (string) => {
  searchPage.enterPatiendID(string);
  searchPage.search();
});

When("I fill first name as {string}", (string) => {
  searchPage.enterFirstName(string);
});

When("I search by first name as {string}", (string) => {
  searchPage.enterFirstName(string);
  searchPage.search();
});

When("I fill dob as {string}", (string) => {
  searchPage.enterDob(string);
});

When("I search by dob as {string}", (string) => {
  searchPage.enterDob(string);
  searchPage.search();
});

When("I search by sex as {string}", (string) => {
  searchPage.selectGender(string);
  searchPage.search();
});

Then("click on clear all button", () => {
  searchPage.clearAll();
});

When("I last name should be {string}", (string) => {
  cy.get("#name.last").should('have.text', string);
});

When("I first name should be {string}", (string) => {
  cy.get("#firstName").should('have.text', string);
});

When("I dob should be {string}", (string) => {
  cy.get("#dateOfBirth").should('have.text', string);
});

Then("I should see Results with the last name {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});

Then("I should see Results with the first name {string}", (string) => {
  cy.get("a").contains(string).should("be.visible");
});

Then("I should see Results with for text {string}", (string) => {
  cy.get("main p").contains(string).should("be.visible");
});

Then("I should see Results with the sex {string}", (string) => {
  cy.get('main').contains(string).should('be.visible');
});

Then("I should see Results with the patient id {string}", (string) => {
  cy.contains(string).should("be.visible");
});

When("I search by phone number as {string}", (string) => {
  searchPage.enterPhone(string);
  searchPage.search();
});

When("I enter email as {string}", (string) => {
  searchPage.enterEmail(string);
  searchPage.search;
});

When("I enter phone number as {string}", (string) => {
  searchPage.enterPhone(string);
  searchPage.search();
});

When("I search by email as {string}", (string) => {
  searchPage.enterEmail(string);
  searchPage.search();
});

Then("I should see Results with the phone number as {string}", (string) => {
  cy.get('p').contains(string).should("be.visible");

});

Then("I should see Results with the email as {string}", (string) => {
  cy.contains(string).should("be.visible");
});

When("I navigate to id section", () => {  
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
    cy.contains(string1).should("be.visible");
  }
);

When("I navigate to race section", () => {
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
  "I search by ethnicity as {string} and race not selected",
  (string) => {
    searchPage.enterEthnicity(string);
    searchPage.search();
  }
);

Then(
  "I search by ethnicity not selected and race {string}",
  (string) => {
    searchPage.enterRace(string);
    searchPage.search();
  }
);

Then("I select for Deleted patient", ()=>{
  searchPage.selectDelete()
  searchPage.search();
})

Then("I select for Superseded patient", ()=>{
  searchPage.selectSuperseded()
  searchPage.search();
})

Then("I sort by {string}", (string) => {
  cy.get('button[data-testid="button"]').eq(1).click();
  cy.wait(500)
  cy.get('button[data-testid="button"]').eq(1).click();
  cy.wait(1000)
})

Then("I verify the sort of patient name", () => {
  cy.get('.result-item_label__4ANhR')
  .invoke('text')
  .then(name => {
    //Sort the names
    const nameArray = name.trim().split('\n');
    //Sort the name alphabetically
    const sortedNames = [...nameArray].sort();

    //Verify that the names are sorted alphabetically
    expect(nameArray).to.deep.equal(sortedNames);
  });
})


Then("I click address tab", () => {    
  searchPage.clickAddressTab()   
})