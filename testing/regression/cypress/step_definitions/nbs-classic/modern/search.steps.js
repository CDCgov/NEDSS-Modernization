import { Then } from "@badeball/cypress-cucumber-preprocessor";
import classicHomePage from "@pages/nbs-classic/home.page";
import classicSearchPatientPage from "cypress/e2e/pages/patient-extended-form/patient.page";

Then("Enter Last Name text box input {string}", (text) => {
    classicHomePage.enterLastName(text)
});

Then("Click on Search in Patient Search pane", () => {
    classicHomePage.clickSearchBtnInPatientSearchPane()
});

Then("Enter First Name text box input {string}", (text) => {
    classicHomePage.enterFirstName(text)
});

Then("Click on Add New button in patient Search pane", () => {
    classicHomePage.clickAddNewBtnInPatientSearchPane()
});

Then("Click on Add new lab report in patient Search pane", () => {
    classicHomePage.clickAddNewLabReportBtnInPatientSearchPane()
});

Then("Sort Search results by {string}", (string) => {
    classicHomePage.clickSortTableOption(string);
});

Then("Verify top Search result by {string}", (string) => {
    classicHomePage.verifyTopAfterSortSearch(string);
});

Then("Verify top Search result is not {string}", (string) => {
    classicHomePage.verifyNoTopAfterSortSearch(string);
});

Then("I click first patient Search results to view profile", () => {
    classicHomePage.copySearchRowInfo();
});

Then("I click search filter result icon", () => {
    // wait for spinner to disappear

    cy.get('span._loading_rd9r9_1', { timeout: 10000 })
      .should('not.exist');

    // For some reason test fails because Search needs to be clicked again
    classicSearchPatientPage.clickSearchBtnInModernizedPatientSearchPane() 

    cy.get('button[aria-label="Filter"]', { timeout: 10000 })
        .should('be.visible')
        .click()

});

Then("I search filter column {string} with {string}", (columnName, string) => {
    cy.get(`input[id="${columnName}"]`).type(string);
    cy.get(`input[id="${columnName}"]`).type('{enter}');    
});

Then("I verify unique search row contains {string}", (string) => {
    cy.get("div#patient-search-results table.usa-table tbody tr").should('have.length', 1);
    cy.get("div#patient-search-results table.usa-table tbody tr td").contains(string);
});

Then("I verify all search rows contains {string}", (string) => {    
    cy.get("div#patient-search-results table.usa-table tbody tr td").contains(string);
});

Then("Clear search filter {string}", (string) => {
    cy.get(`input[id="${string}"]`).siblings('span').click();    
});
