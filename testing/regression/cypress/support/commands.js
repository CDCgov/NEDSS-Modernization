// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
import '@testing-library/cypress/add-commands'

Cypress.Commands.add('selectByLabel', (label, value) => {
    // Find the label text, then find the element it points to
    cy.contains('label', label).invoke('attr', 'for').then((id) => {
        // Check if the target is a standard <select>
        cy.get('body').then(($body) => {
            if ($body.find(`select#${CSS.escape(id)}`).length > 0) {
                // Standard Select
                cy.get(`select#${CSS.escape(id)}`).select(value);
            } else {
                // React Select: Click the input, type, and hit Enter
                cy.get(`input#${CSS.escape(id)}`).click({ force: true }).type(`${value}{enter}`);
            }
        });
    });
});
