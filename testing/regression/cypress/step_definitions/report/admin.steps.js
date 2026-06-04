import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I navigate to manage reports', () => {
    cy.visit('/nbs/ListReport.do');
    cy.contains('Report Name').should('be.visible');
});

When('I click on the {string} report', (name) => {
    cy.contains('a', name).click();
});

Then('I should see the configuration page', () => {
    cy.contains('View Report').should('be.visible');
});

Then('I should see value {string} in the {string} field', (value, label) => {
    cy.findByRole('definition', { name: label }).should('have.text', value);
});

Then('I should see {int} available filters', (filterCount) => {
    // account for header
    cy.findByRole('group', { name: '3. Available filters' })
        .findAllByRole('row')
        .should('have.length', filterCount + 1);
});

When('I click the filter {int} view button', (filterInd) => {
    cy.findAllByRole('button', { name: 'View' }).eq(filterInd).click();
});
