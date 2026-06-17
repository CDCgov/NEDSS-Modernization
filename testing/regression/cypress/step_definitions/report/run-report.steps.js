import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// Navigation steps

When('I navigate to reports', () => {
    cy.visit('/nbs/ManageReports.do');
    cy.contains('Private Reports').should('be.visible');
    cy.contains('Public Reports').should('be.visible');
    cy.contains('Template Reports').should('be.visible');
    cy.contains('Reporting Facility Reports').should('be.visible');
});

Then('I navigate to report with reportUid: {string} and dataSourceUid: {string}', (reportUid, dataSourceUid) => {
    cy.visit(`report/${reportUid}/${dataSourceUid}/run`);
});

// Component input steps

Then('I enter {string} to the From date', (date) => {
    cy.get('input[id=":r3:-from"]').type(date);
});

Then('I enter {string} to the To date', (date) => {
    cy.get('input[id=":r3:-to"]').type(date);
});

Then('I select {string} from the {string} dropdown menu', (value, label) => {
    cy.selectByLabel(label, value);
});

Then('I select the column {string}', (columnName) => {
    cy.get('input[name="column-search"]').clear().type(columnName);
    
    cy.contains('label', columnName)
        .find('input[type="checkbox"]')
        .check({ force: true });
});

Then("I click on the {string} button", (buttonText) => {
    cy.contains('button', buttonText).click();
    cy.wait(1000);
});

// Confirmation steps

Then("I see confirmation the report has run", () => {
    cy.contains('main', 'Your report has run').should('be.visible');
});

