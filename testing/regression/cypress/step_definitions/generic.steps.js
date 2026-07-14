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

When('I type {int} into the {string} field', (value, label) => {
    cy.findByRole('spinbutton', { name: label }).type(value);
});

Then('The {string} {string} should be disabled', (name, role) => {
    cy.findByRole(role, { name }).should('be.disabled');
});

When('I click the {string} button', (name) => {
    cy.findAllByRole('button', { name }).first().click();
});

When('I select radio {string} in the {string} field', (value, label) => {
    cy.findByRole('radiogroup', { name: label }).findByRole('radio', { name: value }).check({ force: true });
});

Then('I should see a modal labelled {string}', (name) => {
    const modalHeadingTextMatcher = (elementText, element) => {
        // remove button and svg tags from modal heading tag
        const copiedElement = element.cloneNode(true);
        copiedElement.querySelectorAll('button, svg').forEach((node) => node.remove());
        const cleanText = copiedElement.textContent.trim();
        return cleanText.includes(name);
    };

    // some modals are 0x0, so not exactly "visible", but it's there
    cy.findByRole('dialog', { name: modalHeadingTextMatcher }).should('have.class', 'is-visible');
});

Then('I should see a {string} labelled {string}', (role, name) => {
    // the dialog itself is 0x0, so not exactly "visible", but it's there
    cy.findByRole(role, { name }).should('be.visible');
});

Then('I should see value {string} in the {string} field', (value, label) => {
    cy.findByRole('definition', { name: label }).should('have.text', value);
});

Then('I should see {string} validation error', (errorMsg) => {
    cy.findAllByRole('alert').filter(`:contains("${errorMsg}")`).should('be.visible');
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

Then('I should see option {string} in the {string} multi-select combobox input field', (value, label) => {
    cy.findByRole('combobox', { name: label })
        .parentsUntil('.multi-select__control')
        .contains('.multi-select__multi-value__label', value)
        .should('be.visible');
});

Then('I am redirected to {string}', (pathname) => {
    cy.location('pathname').should('eq', pathname);
});

Then('I should not see the {string} {string}', (label, role) => {
    cy.findByRole({ role }, { name: label }).should('not.exist');
});

Then('I should see {string} radio selected in the {string} field', (name, label) => {
    cy.findByRole('radiogroup', { name: label }).findByRole('radio', { name }).should('be.checked');
});
