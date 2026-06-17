import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// Navigation steps

When('I navigate to report with reportUid: {string} and dataSourceUid: {string}', (reportUid, dataSourceUid) => {
    cy.visit(`report/${reportUid}/${dataSourceUid}/run`);
});

// Component input steps

When('I enter {string} to the From date', (date) => {
    // Matches any ID ending with "-from", bypassing the React dynamic prefix
    cy.get('input[id$="-from"]').type(date);
});

When('I enter {string} to the To date', (date) => {
    // Matches any ID ending with "-to", bypassing the React dynamic prefix
    cy.get('input[id$="-to"]').type(date);
});

When('I select {string} from the {string} dropdown menu', (value, label) => {
    cy.selectByLabel(label, value);
});

When('I select the column {string}', (columnName) => {
    cy.get('input[name="column-search"]').type(columnName);
    cy.contains('label', columnName)
        .find('input[type="checkbox"]')
        .check({ force: true });
});

When('I click on the {string} button', (buttonText) => {
    cy.contains('button', buttonText).click();
});

// Confirmation steps

Then('I see confirmation the report has run', () => {
    cy.contains('main', 'Your report has run').should('be.visible');
});
