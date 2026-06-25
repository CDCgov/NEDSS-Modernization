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

When('I toggle the {string} field', (label) => {
    cy.findByRole('checkbox', { name: label }).click({ force: true });
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
    // some modals have a close button in header that needs to be removed
    const escapedName = name.replace(/<[^>]*>/g, "");
    const cleanedName = new RegExp(`^${escapedName}`);
    // some modals are 0x0, so not exactly "visible", but it's there
    cy.findByRole('dialog', { name: cleanedName }).should('have.class', 'is-visible');
});

Then('I should see a {string} labelled {string}', (role, name) => {
    // the dialog itself is 0x0, so not exactly "visible", but it's there
    cy.findByRole(role, { name }).should('be.visible');
});

Then('I should see value {string} in the {string} field', (value, label) => {
    cy.findByRole('definition', { name: label }).should('have.text', value);
});

Then('I should see {string} validation error', (errorMsg) => {
    cy.findByRole('alert').should('have.text', errorMsg);
});

Then('I should see a loading indicator', () => {
    cy.findByRole('status').should('have.text', 'Loading');
});

Then('I should see value {string} in the {string} {string} input field', (value, label, role) => {
    cy.findByRole(role, { name: label }).should('have.value', value);
});

Then('I should see option {string} in the {string} combobox input field', (value, label) => {
    cy.findByRole('combobox', { name: label }).find('option:selected').should('have.text', value);
});

Then('I am redirected to {string}', (pathname) => {
    cy.location('pathname').should('eq', pathname);
})

Then('I should not see the {string} {string}', (label, role) => {
    cy.findByRole({role}, { name: label }).should('not.exist');
})