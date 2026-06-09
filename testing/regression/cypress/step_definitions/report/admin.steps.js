import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I navigate to manage reports', () => {
    cy.visit('/nbs/ListReport.do');
    cy.contains('Report Name').should('be.visible');
});

Then('I should see the report list', () => {
    cy.contains('Report Name').should('be.visible');
});

When('I click on the {string} link', (name) => {
    cy.contains('a', name).click();
});

Then('I should see the {string} link', (name) => {
    cy.contains('a', name).should('be.visible');
});

Then('I should see the {string} configuration page', (type) => {
    cy.contains(`${type} Report`).should('be.visible');
});

Then('I should see value {string} in the {string} field', (value, label) => {
    cy.findByRole('definition', { name: label }).should('have.text', value);
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

Then('I should see {int} available filters', (filterCount) => {
    // account for header
    cy.findByRole('group', { name: '3. Available filters' })
        .findAllByRole('row')
        .should('have.length', filterCount + 1);
});

When('I click the filter {int} {string} button', (filterInd, name) => {
    cy.findAllByRole('button', { name }).eq(filterInd).click();
});
