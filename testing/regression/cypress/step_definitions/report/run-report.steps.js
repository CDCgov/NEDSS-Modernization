import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I navigate to reports', () => {
    cy.visit('/nbs/ManageReports.do');
    cy.contains('Private Reports').should('be.visible');
    cy.contains('Public Reports').should('be.visible');
    cy.contains('Template Reports').should('be.visible');
    cy.contains('Report Facility Reports').should('be.visible');
});

Then('I navigate to report with reportUid: {string} and dataSourceUid: {string}', (reportUid, dataSourceUid) => {
    cy.visit(`report/${reportUid}/${dataSourceUid}/run`);
});

Then('I select {string} from the Condition Code dropdown', (condition) => {
    cy.get('input[name="basicFilter.id_14.value"]').type(condition).type('{enter}');
});

Then("I click on the {string} button", (buttonText) => {
    cy.contains('button', buttonText).click();
    cy.wait(1000);
});
