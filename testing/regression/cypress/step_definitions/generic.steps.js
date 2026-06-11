import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I click on the {string} link', (name) => {
    cy.contains('a', name).click();
});

Then('I should see the {string} link', (name) => {
    cy.contains('a', name).should('be.visible');
});

When('I select value {string} in the {string} field', (value, label) => {
    cy.findByRole('combobox', { name: label }).select(value);
});

When('I type {string} into the {string} field', (value, label) => {
    cy.findByRole('textbox', { name: label }).type(value);
});

Then('The {string} {string} should be disabled', (name, role) => {
    cy.findByRole(role, { name }).should('be.disabled');
});

When('I click the {string} button', (name) => {
    cy.findAllByRole('button', { name }).first().click();
});

Then('I should see a modal labelled {string}', (name) => {
    // some modals are 0x0, so not exactly "visible", but it's there
    cy.findByRole('dialog', { name }).should('have.class', 'is-visible');
});

Then('I should see a {string} labelled {string}', (role, name) => {
    // the dialog itself is 0x0, so not exactly "visible", but it's there
    cy.findByRole(role, { name }).should('be.visible');
});

Then('I should see value {string} in the {string} field', (value, label) => {
    cy.findByRole('definition', { name: label }).should('have.text', value);
});
