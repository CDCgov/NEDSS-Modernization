import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I navigate to list reports', () => {
    cy.visit('/nbs/ManageReports.do');
    cy.contains('Private Reports').should('be.visible');
});

Then('I see the {string} report', (title) => {
    cy.contains(title).should('be.visible');
})
